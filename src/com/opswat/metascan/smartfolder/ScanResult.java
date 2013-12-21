package com.opswat.metascan.smartfolder;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * The results of a the current <code>ScanSession</code>.
 * 
 * The progressPercentage and placeInQueue will be updated until the <code>File</code>
 * has been completely scanned.
 * 
 * @author Tristan Currens
 */
public class ScanResult {
    
    private int placeInQueue;
    private int progressPercentage;
    private int resultID;
    private String resultString;
    
    /**
     * Default constructor.
     * 
     * This will parse the given JSON data that was obtained from the Metascan
     * Online server.
     * 
     * @param data the JSON data 
     */
    public ScanResult(String data) {
        JSONObject jsonData = (JSONObject) JSONValue.parse(data);
        JSONObject scanResults = (JSONObject) JSONValue.parse(jsonData.get("scan_results").toString());
        
        placeInQueue        = Integer.parseInt(scanResults.get("in_queue").toString());
        progressPercentage  = Integer.parseInt(scanResults.get("progress_percentage").toString());
        resultID            = Integer.parseInt(scanResults.get("scan_all_result_i").toString());
        resultString        = scanResults.get("scan_all_result_a").toString();
    }
    
    public int getPlaceInQueue() {
        return placeInQueue;
    }
    
    public int getResultID() {
        return resultID;
    }
    
    public int getProgressPercentage() {
        return progressPercentage;
    }
    
    public String getResultString() {
        return resultString;
    }
    
    public boolean isComplete() {
        return progressPercentage >= 100;
    }
    
    public boolean isClean() {
        return resultID == 0 || resultID == 7;
    }
}
