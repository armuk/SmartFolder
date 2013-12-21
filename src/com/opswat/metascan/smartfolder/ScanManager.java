package com.opswat.metascan.smartfolder;

import com.opswat.metascan.smartfolder.settings.SettingsManager;
import com.opswat.metascan.smartfolder.ui.UserInterfaceManager;
import com.opswat.metascan.web.SessionDownloader;
import com.opswat.metascan.web.SessionUploader;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Statically manages all upload and download sessions for the program.
 * 
 * The <code>ScanManager</code> processes all incoming requests to scan files and
 * directories, as well as synchronizing the process flow from upload, to download,
 * and to finalization.
 *
 * @author Tristan Currens
 * @see SessionUploader
 * @see SessionDownloader
 */
public class ScanManager {
    
    /**
     * A map containing the last modified times of the specified <code>File</code>.
     * This map ensures that a <code>File</code> will only be scanned one time unless
     * it is changed.
     */
    private static HashMap<File, Long> fileLastModified = new HashMap();
    
    /**
     * A list of <code>ScanSession</code>s to upload to the Metascan Online server.
     */
    private static LinkedList<ScanSession> sessionsToUpload = new LinkedList();
    
    /**
     * A list of <code>ScanSession</code>s to download from the Metascan Online server.
     */
    private static LinkedList<ScanSession> sessionsToDownload = new LinkedList();
    
    /**
     * Stores whether or not the <code>ScanManager</code> has been initialized.
     */
    private static boolean initialized = false;
    
    /**
     * Pushes a <code>File</code> to be uploaded to the Metascan Online server.
     * 
     * @param file the <code>File</code> to upload 
     * @param bypassFOD whether or not to force a rescan on this file by bypassing
     * the fileOutOfDate check
     */
    public static synchronized void pushFileForUpload(File file, boolean bypassFOD) {
        ScanSession session = new ScanSession(file);
        pushSessionForUpload(session, bypassFOD, true);
    }
    
    /**
     * Pushes a directory to be uploaded to the Metascan Online server.
     * 
     * This will recursively upload all of the files inside this directory and
     * its sub-directories through a series of calls to <code>pushFileForUpload</code>
     * and <code>pushDirectoryForUpload</code>.
     * 
     * @param directory the directory the upload
     * @param bypassFOD whether or not to force a rescan on this file by bypassing
     * the fileOutOfDate check
     */
    public static synchronized void pushDirectoryForUpload(File directory, boolean bypassFOD) {
        if (directory != null && directory.isDirectory() && directory.listFiles() != null) {
            for (File child : directory.listFiles()) {
                if (child.isFile())
                    pushFileForUpload(child, bypassFOD);
                if (child.isDirectory())
                    pushDirectoryForUpload(child, bypassFOD);
            }
        }
    }
    
    /**
     * Pushes a session to be uploaded to the Metascan Online server.
     * 
     * Because this method takes a <code>ScanSession</code> as a parameter, direct calls
     * will only be used for re-uploading sessions that could not previously be uploaded.
     * This case occurs when the user specifies an invalid API key, an API key that
     * has exceeded its limit, or if no Internet connection is present.
     * 
     * @param session the <code>ScanSession</code> to be uploaded
     * @param bypassFOD whether or not to force a rescan on this file by bypassing
     * @param putInTable whether or not this session should be added to the display table
     * 
     * the fileOutOfDate check
     */
    public static synchronized void pushSessionForUpload(ScanSession session, boolean bypassFOD, boolean putInTable) {
        if (!initialized)
            init();
        
        boolean fileOutOfDate = 
                !fileLastModified.containsKey(session.getFile()) || 
                fileLastModified.get(session.getFile()) > session.getFile().lastModified() ||
                bypassFOD;
        
        if (fileOutOfDate) {
            fileLastModified.put(session.getFile(), session.getFile().lastModified());
            sessionsToUpload.offer(session);
            if (putInTable)
                UserInterfaceManager.getSmartFolderFrame().getConfigurationPanel().pushSessionToLog(session);
        }
        
    }
    
    /**
     * Pushes a <code>ScanSession</code> to have its results downloaded from the
     * Metascan Online server.
     * 
     * @param session the <code>ScanSession</code> for which to download results
     */
    public static synchronized void pushSessionForDownload(ScanSession session) {
        if (session.getDataID() != null)
            sessionsToDownload.offer(session);
        UserInterfaceManager.getSmartFolderFrame().getConfigurationPanel().updateSessionStatus(session, "Scanning");
    }
    
    /**
     * Gets the next <code>ScanSession</code> to have its <code>File</code> uploaded
     * to the Metascan Online server.
     * 
     * @return the next <code>ScanSession</code> that is awaiting upload or <code>null</code>
     * if there are no more <code>ScanSession</code>s.
     */
    public static synchronized ScanSession getNextSessionForUpload() {
        if (!sessionsToUpload.isEmpty() && SettingsManager.hasApiKey())
            return sessionsToUpload.pollFirst();
        else if (!SettingsManager.hasApiKey())
            UserInterfaceManager.notifyInvalidAPIKey();
        return null;
    }
    
    /**
     * Gets the next <code>ScanSession</code> to have its results downloaded from
     * the Metascan Online server.
     * 
     * @return the next <code>ScanSession</code> that is awaiting download or <code>null</code>
     * if there are no more <code>ScanSession</code>s.
     */
    public static synchronized ScanSession getNextSessionForDownload() {
        if (!sessionsToDownload.isEmpty() && SettingsManager.hasApiKey())
            return sessionsToDownload.pollFirst();
        else if (!SettingsManager.hasApiKey())
            UserInterfaceManager.notifyInvalidAPIKey();
        return null;
    }
    
    /**
     * Finalizes the specified <code>ScanSession</code>.
     * 
     * This will alert the user of a suspicious file if the results are not clean.
     * 
     * @param scanSession the <code>ScanSession</code> to finalize
     */
    public static synchronized void finalizeSession(ScanSession scanSession) {
        if (!scanSession.getScanResult().isClean()) {
            UserInterfaceManager.getSmartFolderFrame().getConfigurationPanel().updateSessionStatus(scanSession, "Infected");
            UserInterfaceManager.getSmartFolderFrame().getConfigurationPanel().updateSessionAction(scanSession, "");
            
            UserInterfaceManager.getSmartFolderFrame().getVirusNotificationPanel().pushVirusToTable(scanSession);
        } else {
            UserInterfaceManager.getSmartFolderFrame().getConfigurationPanel().updateSessionStatus(scanSession, "Clean");
            UserInterfaceManager.getSmartFolderFrame().getConfigurationPanel().updateSessionAction(scanSession, "Ignored");
        }
    }
    
    /**
     * Initializes the <code>ScanManager</code>. This will start the <code>SessionUploader</code>
     * and the <code>SessionDownloader</code>.
     * 
     * @see SessionDownloader
     * @see SessionUploader
     */
    private static void init() {
        SessionUploader.start();
        SessionDownloader.start();
        
        initialized = true;
    }
}
