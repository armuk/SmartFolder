package com.opswat.metascan.smartfolder;

import com.opswat.metascan.smartfolder.settings.SettingsManager;
import com.opswat.metascan.smartfolder.ui.UserInterfaceManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Monitors specified directories on the user's system for any changes or deletions. <br><br>
 * 
 * The <code>DirectoryMonitor</code> uses the following logic for determining what
 * to do with changed files:<ul>
 * 
 * <li><b>Files</b></li><ol>
 *      <li><b>Creation: </b> Scan the file</li>
 *      <li><b>Update:   </b> Scan the file</li></ol>
 * 
 * <li><b>Directories</b></li><ol>
 *      <li><b>Creation: </b> Register the directory to be watched</li>
 *      <li><b>Rename:   </b> Remove references to the old name of the directory
 *                            and adds new references to the new name of the directory
 *      <li><b>Deletion: </b> Remove references to the directory</li></ul>
 * 
 * @author Tristan Currens
 */
public class DirectoryMonitor {
    
    /**
     * A list of directories currently being watched by the <code>DirectoryMonitor</code>.
     */
    private static ArrayList<File> registeredDirectories = new ArrayList();
    
    /**
     * A mapping from <code>Path</code> to <code>WatchKey</code>.
     * 
     * This <code>HashMap</code> will contain one entry for each directory currently
     * being monitored by the <code>DirectoryMonitor</code>.
     */
    private static HashMap<Path, WatchKey> pathToWatchKey   = new HashMap();
    
    /**
     * A mapping from <code>WatchKey</code> to <code>Path</code>.
     * 
     * This <code>HashMap</code> will contain one entry for each directory currently
     * being monitored by the <code>DirectoryMonitor</code>.
     */
    private static HashMap<WatchKey, Path> watchKeyToPath   = new HashMap();
    
    /**
     * The <code>WatchService</code> is responsible for storing events thrown by
     * changes within directories.
     */
    private static WatchService watchService;
    
    /**
     * Stores whether or not the <code>DirectoryMonitor</code> has been initialized.
     */
    private static boolean running = false;
    
    /**
     * Registers the specified directory with the <code>WatchService</code>.
     * 
     * Registering a directory will create a new <code>WatchKey</code> that will
     * alert the <code>WatchService</code> when a change has been made to the 
     * directory. This method will also recursively register any sub-directories
     * as well as scanning any <code>File</code>s that are encountered along the
     * way.
     * 
     * @param directory the directory to register
     */
    public static void registerDirectory(File directory) {
        // ensures that the specified File is a directory and that it isn't
        // currently registered
        if (directory.isDirectory() && !registeredDirectories.contains(directory)) {
            if (!running)
                start();
            
            try {
                Path directoryPath = directory.toPath();    // the Path to the directory
                
                // the WatchKey that monitors this directory
                WatchKey watchKey = directoryPath.register(
                        watchService, 
                        StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_MODIFY,
                        StandardWatchEventKinds.ENTRY_DELETE);

                // put the Path and WatchKey into a two-way mapping
                pathToWatchKey.put(directoryPath, watchKey);
                watchKeyToPath.put(watchKey, directoryPath);
                
                // add the directory to the list of registered directories, update
                // the user interface, and store the directory in the user's settings
                // so that the directory will automatically be registered the next
                // time the user starts the program
                registeredDirectories.add(directory);
                UserInterfaceManager.getSmartFolderFrame().getSmartFolderPanel().updateMonitoredDirectoriesTree(registeredDirectories);
                SettingsManager.updateSmartFolders();

                // recursive step
                for (File child : directory.listFiles())
                    if (child.isFile())
                        ScanManager.pushFileForUpload(child, false);
                    else if (child.isDirectory())
                        registerDirectory(child);
            } catch (IOException e) {}
        }
    }
    
    /**
     * Unregisters the specified directory from the <code>WatchService</code>.
     * 
     * A directory can only be unregistered if the following conditions are true:<ol><br>
     * <li>The directory is currently registered</li>
     * <li>The directory's parent is not register</li></ol><br>
     * 
     * The exception to these conditions is if the directory no longer exists.
     * This will allow a sub-directory to be removed from the <code>WatchService</code>
     * if it was deleted from the system.
     * 
     * @param directory the directory to unregister
     */
    public static void unregisterDirectory(File directory) {
        if ((pathToWatchKey.containsKey(directory.toPath()) && !pathToWatchKey.containsKey(directory.getParentFile().toPath())) || !directory.exists()) {
            // stop watching this directory
            WatchKey key = pathToWatchKey.get(directory.toPath());     
            key.cancel();
            
            // remove references to the directory
            pathToWatchKey.remove(directory.toPath());              
            watchKeyToPath.remove(key);
        
            // recursively unregister the sub-directories of this directory
            for (int i = registeredDirectories.size() - 1; i >= 0; i--)
                if (registeredDirectories.get(i).getParent().equals(directory.getPath()))
                    unregisterDirectory(registeredDirectories.get(i));
            
            // remove the directory from the list of watched directories, update
            // the user interface, and save the SmartFolders to the user's settings
            registeredDirectories.remove(directory);
            UserInterfaceManager.getSmartFolderFrame().getSmartFolderPanel().updateMonitoredDirectoriesTree(registeredDirectories);
            SettingsManager.updateSmartFolders();
        }
    }
    
    /**
     * Attempts to start the <code>DirectoryMonitor</code>.
     * 
     * This will create a new <code>Thread</code> that will loop through and process 
     * events that are detected within the monitored directories.
     */
    private static void start() {
        if (!running) {
            try {
                watchService = FileSystems.getDefault().newWatchService();
            } catch (IOException e) {}
            running = true;
            Thread smartFolderMonitorThread = new Thread() {
                @Override
                public void run() {
                    while (true)     
                        processNextKey();
                }
            };
            smartFolderMonitorThread.start();
        }
    }
    
    /**
     * Processes the next <code>WatchKey</code> based on the logic specified in the class header.
     */
    private static void processNextKey() {
        WatchKey nextKey;
        try {            
            nextKey = watchService.take();  // wait for the next key to throw an event
            
            // iterate the events that occured on this key
            for (WatchEvent watchEvent : nextKey.pollEvents()) {
                WatchEvent<Path> ev = (WatchEvent<Path>) watchEvent;
                
                // get the file responsible for this event
                Path fullPath       = watchKeyToPath.get(nextKey).resolve(ev.context());
                File responsibleFile = fullPath.toFile();
                
                // if a directory was deleted, remove references to the directory
                if (!responsibleFile.exists()){
                    if (registeredDirectories.contains(responsibleFile))
                        unregisterDirectory(responsibleFile);
                } 
                
                // if a new directory was created, watch the directory
                else if (responsibleFile.isDirectory()) {
                    if (ev.kind().equals(StandardWatchEventKinds.ENTRY_CREATE))
                        registerDirectory(responsibleFile);
                } 
                
                // if file was created or modified, scan the file
                else if (responsibleFile.isFile()) {
                    ScanManager.pushFileForUpload(responsibleFile, false);
                }
            }
            
            nextKey.reset();    // reset the key in order to process the next one
        } catch (InterruptedException | NullPointerException e) {}
    }    
}
