package com.opswat.metascan.smartfolder.devicedetection;

import com.opswat.metascan.smartfolder.ScanManager;
import com.opswat.metascan.smartfolder.ui.SmartFolderFrame;
import com.opswat.metascan.smartfolder.ui.UserInterfaceManager;
import com.opswat.metascan.smartfolder.ui.panel.DeviceDetectionPanel;
import java.io.File;

/**
 * Monitors the user's computer and scans any new media devices that are plugged
 * in or otherwise added to the system. <br><br>
 * 
 * The <code>DeviceDetectionManager</code> is started and stopped by toggling 
 * the "Automatically Detect and Scan New Devices" box on the 
 * <code>SmartFolderFrame</code> under the <code>DeviceDetectionPanel</code>.
 * 
 * @author Tristan Currens (tristan.currens@ttu.edu)
 * @see SmartFolderFrame
 * @see DeviceDetectionPanel
 */
public class DeviceDetectionManager {
    
    /**
     * The amount of time in milliseconds for the <code>DeviceDetectionManager</code>'s
     * <code>Thread</code> to wait between each attempt to check for new devices. 
     * 
     * The default for this <code>Thread</code> is 5 seconds. Decreasing this value will
     * increase the amount of processor time used by the <code>Thread</code>, and can
     * lead to decreased performance on lower-end systems.
     */
    private static final long SLEEP_TIME_MILLIS = 5000;
    
    /**
     * An array of device letters to be used on this system. 
     * 
     * This alphabet omits the letter C, to avoid accidentally scanning an entire system.
     */
    private static final String[] DEVICE_LETTERS = "ABDEFGHIJKLMNOPQRSTUVWXYZ".split("");
    
    /**
     * An array of device <code>File</code>s to be used to check if a device is active.
     */
    private static final File[] devices = new File[DEVICE_LETTERS.length];
    
    /**
     * An array of values used to store the state of each drive from the past run.
     * 
     * This is used to check for transitions from inactive to active or vice versa.
     */
    private static boolean[] deviceEnabled = new boolean[DEVICE_LETTERS.length];
    
    /**
     * Stores whether or not the <code>DeviceDetectionManager</code> is currently running. 
     * 
     * This value can be flagged by <code>DeviceDetectionManager.stop()</code> to tell 
     * the <code>DeviceDetectionManager</code> to stop accepting new devices.
     */
    private static boolean running = false;
    
    /**
     * Attempts to start the <code>DeviceDetectionManager</code>. 
     * 
     * If the <code>DeviceDetectionManager</code> is not currently running, a new
     * <code>Thread</code> will be started to loop over the possible device locations.<br><br>
     * 
     * On start, the <code>DeviceDetectionManager</code> will take a base-line
     * of all existing devices on the user's system. This will keep the
     * <code>DeviceDetectionManager</code> from scanning large shared drives or
     * external hard drives.
     */
    public static void start() {
        if (!running) {
            running = true;
            Thread deviceDetectionManagerThread = new Thread() {
                @Override
                public void run() {
                    // create a baseline of currently mounted devices
                    for (int i = 0; i < DEVICE_LETTERS.length; i++) {
                        devices[i] = new File(DEVICE_LETTERS[i] + ":\\");
                        deviceEnabled[i] = devices[i].exists();
                    }

                    // while stop() has not been called
                    while (running) {
                        // check for device state transitions, then wait
                        checkDevices();
                        try {
                            Thread.sleep(SLEEP_TIME_MILLIS);
                        }catch(InterruptedException e){}
                    }
                }

            };
            deviceDetectionManagerThread.start();
        }
    }
    
    /**
     * Attempts to stop a running <code>DeviceDetectionManager</code>. 
     * 
     * This will interrupt any current device checks that are being run.
     */
    public static void stop() {
        running = false;
    }
    
    /**
     * Checks if the <code>DeviceDetectionManager</code> is currently running.
     * 
     * @return <code>true</code> if it is; <code>false</code> otherwise
     */
    public static boolean isRunning() {
        return running;
    }
    
    /**
     * Checks the status of all of the devices on the user's system. If the device
     * didn't exist on the last pass, and exists now, then the device will be scanned
     * and displayed on the <code>DeviceDetectionPanel</code>.
     */
    private static void checkDevices() {
        // loop through the possible drive letters as long as the DeviceDetectionManager
        // is running
        for (int i = 0; i < devices.length && running; i++) {
            
            // if the device exists now, and didn't used to (ie. the device is new)
            if (devices[i].exists() && !deviceEnabled[i]) {
                UserInterfaceManager.getSmartFolderFrame().getDeviceDetectionPanel().addDeviceToTable(devices[i]);
                ScanManager.pushDirectoryForUpload(devices[i], true);
            } 
            
            // if the device doesn't exist now, and used to (ie. the device was removed)
            else {
                UserInterfaceManager.getSmartFolderFrame().getDeviceDetectionPanel().removeDeviceFromTable(devices[i]);
            }
            
            deviceEnabled[i] = devices[i].exists();     // store the value for the next pass
        }
    }
}
