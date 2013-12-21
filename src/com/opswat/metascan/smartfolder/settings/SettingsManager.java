package com.opswat.metascan.smartfolder.settings;

import com.opswat.metascan.smartfolder.devicedetection.DeviceDetectionManager;
import com.opswat.metascan.smartfolder.ScanManager;
import com.opswat.metascan.smartfolder.DirectoryMonitor;
import com.opswat.metascan.smartfolder.ui.UserInterfaceManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Stores the settings that were specified by the user.
 * 
 * This will allow the user's settings (including which directories to monitor)
 * to carry over from one run to another.
 *
 * @author Tristan Currens
 */
public class SettingsManager {
    
    /**
     * The location of the settings file on the user's system.
     * 
     * ("C:\Users\[UserName]\AppData\Local\Temp\OPSWAT\Metascan\settings.properties" by default)
     */
    private static final String SETTINGS_FILE_LOCATION = System.getProperty("java.io.tmpdir") + "OPSWAT\\Metascan\\settings.properties";
    
    /**
     * The API key used to connect to the Metascan Online server.
     */
    private static String apiKey = "";
    
    /**
     * Stores whether or not the <code>SmartFolderPanel</code> should be displayed on 
     * program startup.
     */
    private static boolean hideMenuOnStartupEnabled = false;
    
    /**
     * Stores whether or not the <code>DeviceDetectionManager</code> should be started
     * on program startup.
     */
    private static boolean autoScanNewDevicesEnabled = false;
    
    /**
     * An array of the current top-level directories to monitor. 
     * 
     * This list will not include every currently monitored directory, only the ones 
     * closest to the file root as subsequent sub-folders will be automatically 
     * registered along with the top-level folders.
     */
    private static File[] smartFolders = new File[0];
    
    /**
     * Loads the current settings from the system.
     * 
     * If the settings <code>File</code> does not exist, then a new <code>File</code>
     * will be created with default settings. After loading, the settings will be
     * pushed to the user interface and acted upon.
     */
    public static void loadSettings() {
        try {
            File settingsFile = new File(SETTINGS_FILE_LOCATION);
            
            // if the file doesn't exist yet
            if (!settingsFile.exists()) {
                settingsFile.getParentFile().mkdirs();  // make the path to the file
                settingsFile.createNewFile();           // make the file
                saveSettings();                         // save the default settings
            }
            
            // load the settings
            Properties properties = new Properties();
            properties.load(new FileInputStream(settingsFile));
            
            apiKey                      = properties.get("apiKey").toString();
            hideMenuOnStartupEnabled    = properties.get("hideMenuOnStartupEnabled").equals("true");
            autoScanNewDevicesEnabled   = properties.get("autoScanNewDevicesEnabled").equals("true");
            String smartFoldersList     = properties.get("smartFolders").toString();
            
            setApiKey(apiKey);
            setHideMenuOnStartupEnabled(hideMenuOnStartupEnabled);
            setAutoScanNewDevicesEnabled(autoScanNewDevicesEnabled);
            
            // parse the Paths of the top level SmartFolders
            String[] smartFoldersSplit = smartFoldersList.split("\\,");
            smartFolders = new File[smartFoldersSplit.length];
            
            // Note: these two loops need to remain separate to avoid overwriting
            // the settings file before all directories are registered
            
            // create File references to the specified path
            for (int i = 0; i < smartFoldersSplit.length; i++)
                smartFolders[i] = new File(smartFoldersSplit[i]);
            
            // register the SmartFolders
            for (File smartFolder : smartFolders)
                DirectoryMonitor.registerDirectory(smartFolder);
        } catch (IOException e) {}
    }
    
    /**
     * Saves the current settings to the user's system.
     */
    private static void saveSettings() {
        try {
            File settingsFile = new File(SETTINGS_FILE_LOCATION);
            Properties properties = new Properties();
            
            // store the settings
            properties.put("apiKey", apiKey);
            properties.put("hideMenuOnStartupEnabled", hideMenuOnStartupEnabled + "");
            properties.put("autoScanNewDevicesEnabled", autoScanNewDevicesEnabled + "");
            
            // make the top-level directories into a String separated by commas
            String smartFoldersString = "";
            for (int i = 0; i < smartFolders.length; i++) {
                smartFoldersString += smartFolders[i].getPath();
                if (i != smartFolders.length - 1)
                    smartFoldersString += ",";
            }
            properties.put("smartFolders", smartFoldersString);
            
            // save
            properties.store(new FileOutputStream(settingsFile), "");
        } catch (IOException e) {}
    }
    
    /**
     * Sets the API key to the newly specified value.
     * 
     * If the new API key is different than the old one and isn't of length 0,
     * then all currently monitored directories will be scanned. This is to safe
     * guard against changing API keys or correcting an incorrect API key.
     * 
     * @param apiKey the new API key
     */
    public static void setApiKey(String apiKey) {        
        // set the value of field in the User Interface
        UserInterfaceManager.getSmartFolderFrame().getConfigurationPanel().setApiKeyFieldText(apiKey);
        
        // store the value
        SettingsManager.apiKey = apiKey;
        
        UserInterfaceManager.getSmartFolderFrame().setAPIKeyLock(apiKey.isEmpty());
        
        saveSettings();
    }
    
    public static void setHideMenuOnStartupEnabled(boolean value) {
        UserInterfaceManager.getSmartFolderFrame().getConfigurationPanel().setHideWindowOnStartup(value);
        
        hideMenuOnStartupEnabled = value;
        saveSettings();
    }
    
    /**
     * Updates the autoscanNewDevices property.
     * 
     * If this property is toggled from on to off, then the <code>DeviceDetectionManager</code>
     * will be started.
     * 
     * If this property is toggled from off to on, then the <code>DeviceDetectionManager</code>
     * will be stopped.
     * 
     * @param value the new value
     */
    public static void setAutoScanNewDevicesEnabled(boolean value) {
        UserInterfaceManager.getSmartFolderFrame().getDeviceDetectionPanel().setAutoscanNewDevices(value);
        
        if (value && !DeviceDetectionManager.isRunning())
            DeviceDetectionManager.start();
        
        if (!value && DeviceDetectionManager.isRunning())
            DeviceDetectionManager.stop();
        
        autoScanNewDevicesEnabled = value;
        saveSettings();
    }
    
    public static void updateSmartFolders() {
        smartFolders = UserInterfaceManager.getSmartFolderFrame().getSmartFolderPanel().getTopLevelSmartFolders();
        saveSettings();
    }
     
    public static boolean hasApiKey() {
        return !apiKey.isEmpty();
    }
    
    public static String getApiKey() {
        return apiKey;
    }

    public static boolean isHideMenuOnStartupEnabled() {
        return hideMenuOnStartupEnabled;
    }
}
