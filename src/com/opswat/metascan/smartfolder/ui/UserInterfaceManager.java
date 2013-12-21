package com.opswat.metascan.smartfolder.ui;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Statically stores the User Interface window and other methods used to display
 * windows.
 *
 * @author Tristan Currens
 */
public class UserInterfaceManager {
    /**
     * The icon that will be displayed in the <code>SystemTray</code>.
     */
    public static final ImageIcon OPSWAT_LOGO_16  = new ImageIcon(UserInterfaceManager.class.getClass().getResource("/img/opswat_logo_16.png"));
    
    /**
     * The User Interface window.
     */
    private static SmartFolderFrame SMART_FOLDER_FRAME;
    
    /**
     * The <code>Object</code> that will be displayed in the <code>SystemTray</code>.
     */
    private static TrayIcon trayIcon = new TrayIcon(OPSWAT_LOGO_16.getImage());
    
    private static boolean userNotifiedOfAPIKey = false;
    
    /**
     * Initializes all of the necessary User Interface elements.
     * 
     * This method will set the Look and Feel to the default look and feel for 
     * the user's system, create the User Interface window, and put an icon in 
     * the <code>SystemTray</code>.
     */
    public static void init() {
        updateLAF();
        
        SMART_FOLDER_FRAME = new SmartFolderFrame();
        createTrayIcon();
    }
    
    /**
     * Displays the specified <code>Frame</code> centered on the user's screen.
     * 
     * @param frame the <code>Frame</code>
     */
    public static void displayAndCenter(Frame frame) {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    /**
     * Displays a notification to the user indicating that they should act on 
     * potential threats on their system.
     */
    public static void notifyUserOfVirus() {
        trayIcon.displayMessage(
                "SmartFolder Virus Detection", 
                "One or more suspicious files have been detected on your system.\n\n"
                + "Click here to review the files.", 
                TrayIcon.MessageType.WARNING);
    }
    
    /**
     * Displays a notification to the user indicating that they should act on 
     * an invalid API key
     */
    public static void notifyInvalidAPIKey() {
        if (!userNotifiedOfAPIKey) {
            userNotifiedOfAPIKey = true;
            trayIcon.displayMessage(
                    "SmartFolder Virus Detection",
                    "The API Key that you specified is invalid.",
                    TrayIcon.MessageType.WARNING);
        }
    }
    
    /**
     * Displays a notification to the user indicating that SmartFolder is still
     * running, even when closed out of.
     */
    public static void notifyOfBackground() {
        trayIcon.displayMessage(
                "SmartFolder Virus Detection",
                "Metascan Online SmartFolder is still running in the background. SmartFolder"
                + " will continue to monitor your system until closed through the system tray.",
                TrayIcon.MessageType.INFO);
    }
    
    public static void resetAPIKeyNotification() {
        userNotifiedOfAPIKey = false;
    }
    
    
    public static SmartFolderFrame getSmartFolderFrame() {
        return SMART_FOLDER_FRAME;
    }
    
    /**
     * Creates an icon with a <code>PopupMenu</code> in the user's <code>SystemTray</code>.
     * 
     * Clicking on the icon will bring up a menu that will allow the user to open
     * the User Interface window or close the program. Double clicking the icon
     * will bring up the User Interface.
     */
    private static void createTrayIcon() {
        try {
            PopupMenu popupMenu = new PopupMenu();

            // opens the User Interface
            MenuItem smartFolderMenuItem = new MenuItem("Metascan Online SmartFolder");
            smartFolderMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if (!SMART_FOLDER_FRAME.isVisible())
                        displayAndCenter(SMART_FOLDER_FRAME);
                }
            });
            popupMenu.add(smartFolderMenuItem);
            
            popupMenu.addSeparator();
            
            // exits the program
            MenuItem exitMenuItem = new MenuItem("Quit");
            exitMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    System.exit(0);
                }
            });
            popupMenu.add(exitMenuItem);
            
            trayIcon.setPopupMenu(popupMenu);
            trayIcon.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if (!SMART_FOLDER_FRAME.isVisible()) {
                        SMART_FOLDER_FRAME.swapToDefaultView();
                        displayAndCenter(SMART_FOLDER_FRAME);
                    } else {
                        SMART_FOLDER_FRAME.setState(Frame.NORMAL);
                    }
                }
            });
            SystemTray.getSystemTray().add(trayIcon);
        } catch (AWTException e) {}
    }
    
    /**
     * Sets the <code>LookAndFeel</code> of the User Interface.
     */
    private static void updateLAF() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {}
    }
}
