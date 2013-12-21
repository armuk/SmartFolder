package com.opswat.metascan.web;

import com.opswat.metascan.smartfolder.ScanManager;
import com.opswat.metascan.smartfolder.ScanResult;
import com.opswat.metascan.smartfolder.ScanSession;
import java.util.ArrayList;

/**
 * Responsible for checking the <code>ScanManager</code> for <code>ScanSession</code>s
 * for which to download results.
 *
 * @author Tristan Currens
 */
public class SessionDownloader {
    
    /**
     * The amount of time (in milliseconds) to wait between each attempt to download
     * results for all of the currently existing <code>ScanSession</code>s.
     * 
     * The default for this <code>Thread</code> is 5 seconds. Decreasing this value will
     * increase the amount of processor time used by the <code>Thread</code>, and can
     * lead to decreased performance on lower-end systems.
     */
    private static final long SLEEP_TIME_MILLIS = 5000;
    
    private static boolean running = false;
    
    /**
     * Starts the <code>SessionDownloader</code>.
     * 
     * This will create a new <code>Thread</code> that will loop through the
     * <code>ScanSession</code>s that are currently present in the <code>ScanManager</code>.
     * It will attempt to download results for all existing sessions.
     */
    public static void start() {
        if (! running) {
            running = true;
            Thread sessionDownloaderThread = new Thread() {
                @Override
                public void run() {
                    while (running) {
                        downloadResults();
                        try {
                            Thread.sleep(SLEEP_TIME_MILLIS);
                        }catch(InterruptedException e){}
                    }
                }
            };
            sessionDownloaderThread.start();
        }
    }
    
    /**
     * Attempts to loop through and download results for the <code>ScanSession</code>s 
     * that are currently present in the <code>ScanManager</code>.
     * 
     * If a session is found to be complete, it will be sent back to the
     * <code>ScanManager</code> for finalization. If a session is not complete, it
     * will be recycled back to have results re-downloaded.
     */
    private static void downloadResults() {
        ArrayList<ScanSession> incompleteSessions = new ArrayList();
        
        ScanSession session;
        while ((session = ScanManager.getNextSessionForDownload()) != null) {
            ScanSession updatedSession = download(session);
            if (updatedSession != null) {
                if (updatedSession.isComplete())
                    ScanManager.finalizeSession(session);
                else
                    incompleteSessions.add(session);
            } else {
                incompleteSessions.add(session);
            }
        }
        
        for (ScanSession incompleteSession : incompleteSessions)
            ScanManager.pushSessionForDownload(incompleteSession);
    }
    
    /**
     * Attempts to download results for the specified <code>ScanSession</code>.
     * 
     * @param session the <code>ScanSession</code> for which results should be downloaded
     * @return an updated <code>ScanSession</code> with the newly updated results
     */
    private static ScanSession download(ScanSession session) {
        ScanResult scanResult = MetascanOnlineInterface.downloadResults(session);
        if (scanResult != null) {
            session.setScanResult(scanResult);
            return session;
        }
        return null;
    }
}
