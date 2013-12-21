package com.opswat.metascan.web;

import com.opswat.metascan.smartfolder.ScanResult;
import com.opswat.metascan.smartfolder.ScanSession;
import com.opswat.metascan.smartfolder.settings.SettingsManager;
import com.opswat.metascan.smartfolder.ui.UserInterfaceManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * Handles communication between the program and the Metascan Online server.
 * 
 * This is used to upload <code>File</code>s and to download results.
 *
 * @author Tristan Currens
 */
public class MetascanOnlineInterface {
    
    /**
     * The location of the API on the Metascan server.
     */
    private static final String SCAN_REQUEST_URL_BASE = "https://api.metascan-online.com/v1/file";
    
    /**
     * Uploads a session to the Metascan Online Server and returns the resulting 
     * data ID. This data ID will be used to fetch the results of the data.
     * 
     * @param session the <code>ScanSession</code> to upload
     * @return the resulting data ID
     */
    public static String uploadSession(ScanSession session) {
        File file = session.getFile();
        
        try {
            URL url = new URL(SCAN_REQUEST_URL_BASE);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            
            urlConnection.addRequestProperty("apikey", SettingsManager.getApiKey());
            urlConnection.addRequestProperty("filename", file.getName());
            
            urlConnection.getOutputStream().write(Files.readAllBytes(file.toPath()));
            
            if (urlConnection.getResponseCode() == 200) {
                String returnData = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())).readLine();
                JSONObject json = (JSONObject) JSONValue.parse(returnData);
                
                return json.get("data_id").toString();
            } else if (urlConnection.getResponseCode() == 401){
                UserInterfaceManager.getSmartFolderFrame().getConfigurationPanel().updateSessionStatus(session, "Unable to Upload");
                UserInterfaceManager.notifyInvalidAPIKey();
                SettingsManager.setApiKey("");
            }
        } catch (IOException e) {}
        
        return null;
    }
    
    /**
     * Downloads the results of the scan from the Metascan Online Server. This will
     * return the results of the scan as a <code>ScanResult</code>.
     * 
     * @param ScanSession the <code>ScanSession</code> to download results for
     * @return the current results of the scan
     */
    public static ScanResult downloadResults(ScanSession session) {
        try {
            URL url = new URL(SCAN_REQUEST_URL_BASE + "\\" + session.getDataID());
            
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.addRequestProperty("apikey", SettingsManager.getApiKey());
            urlConnection.addRequestProperty("data_id", session.getDataID());
            
            if (urlConnection.getResponseCode() == 200) {
                String returnData = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())).readLine();
                
                return new ScanResult(returnData);
            } else if (urlConnection.getResponseCode() == 401){
                UserInterfaceManager.getSmartFolderFrame().getConfigurationPanel().updateSessionStatus(session, "Unable to Download");
                UserInterfaceManager.notifyInvalidAPIKey();
                SettingsManager.setApiKey("");
            }
        } catch (IOException e) {}
        return null;
    }
}
