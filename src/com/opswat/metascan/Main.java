package com.opswat.metascan;

import com.opswat.metascan.smartfolder.settings.SettingsManager;
import com.opswat.metascan.smartfolder.ui.UserInterfaceManager;

/**
 * @author Tristan Currens (tristan.currens@ttu.edu)
 * @version 1.1
 * @since 11/05/2013
 */
public class Main {
    /**
     * Main entry method.
     * 
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        UserInterfaceManager.init();    // intialize the UserInterface
        SettingsManager.loadSettings(); // load the user's settings
        
        // if the user specified that they would like to display the window on every startup,
        // then the UI will be displayed; otherwise it will be hidden until the user opens 
        // the panel using the system tray icon
        if (!SettingsManager.isHideMenuOnStartupEnabled())
            UserInterfaceManager.displayAndCenter(UserInterfaceManager.getSmartFolderFrame());
    }
}