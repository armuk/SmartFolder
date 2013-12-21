package com.opswat.metascan.web;

import com.opswat.metascan.smartfolder.ScanManager;
import com.opswat.metascan.smartfolder.ScanSession;
import com.opswat.metascan.smartfolder.ui.UserInterfaceManager;

/**
 * Responsible for checking the <code>ScanManager</code> for <code>ScanSession</code>s
 * to upload.
 * 
 * @author Tristan Currens
 */
public class SessionUploader {
    
    /**
     * The amount of time (in milliseconds) to wait between each attempt to upload
     * all of the currently existing <code>File</code>s.
     * 
     * The default for this <code>Thread</code> is 5 seconds. Decreasing this value will
     * increase the amount of processor time used by the <code>Thread</code>, and can
     * lead to decreased performance on lower-end systems.
     */
    private static final long SLEEP_TIME_MILLIS = 5000;
    
    private static boolean running = false;
    
    /**
     * Starts the <code>SessionUploader</code>.
     * 
     * This will create a new <code>Thread</code> that will loop through the
     * <code>File</code>s that are currently present in the <code>ScanManager</code>.
     * It will attempt to upload data for all existing <code>File</code>s.
     */
    public static void start() {
        if (!running){
            Thread sessionUploaderThread = new Thread() {
                @Override
                public void run() {
                    running = true;
                    while (running) {
                        uploadSessions();
                        try {
                            Thread.sleep(SLEEP_TIME_MILLIS);
                        }catch(InterruptedException e){}
                    }
                }
            };
            sessionUploaderThread.start();
        }
    }
    
    /**
     * Attempts to loop through and upload data for the <code>File</code>s 
     * that are currently present in the <code>ScanManager</code>.
     * 
     * If a <code>File</code> cannot be uploaded, it will be recycled back to the
     * <code>ScanManager</code> to be uploaded at a later time. This is usually
     * the case when an API key is invalid, or no Internet connection exists
     */
    private static void uploadSessions() {
        ScanSession session;
        while ((session = ScanManager.getNextSessionForUpload()) != null) {
            UserInterfaceManager.getSmartFolderFrame().getConfigurationPanel().updateSessionStatus(session, "Uploading");
            String dataID = upload(session);
            
            if (dataID != null) {
                session.setDataID(dataID);
                ScanManager.pushSessionForDownload(session);
            } else {
                ScanManager.pushSessionForUpload(session, true, false);
            }
        }
    }
    
    /**
     * Attempts to upload the specified <code>ScanSession</code>.
     * 
     * @param session the <code>ScanSession</code> to upload
     * @return the dataID for the scan
     */
    private static String upload(ScanSession session) {
        return MetascanOnlineInterface.uploadSession(session);
    }
}
