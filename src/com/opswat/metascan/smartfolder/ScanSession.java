package com.opswat.metascan.smartfolder;

import java.io.File;

/**
 * Stores information about a virus scan.
 * 
 * @author Tristan Currens
 */
public class ScanSession {
    
    private File file;
    private String dataID;
    private ScanResult scanResult;
    
    public ScanSession(File file) {
        this.file = file;
    }
    
    public File getFile() {
        return file;
    }
    
    public void setDataID(String dataID) {
        this.dataID = dataID;
    }
    
    public String getDataID() {
        return dataID;
    }
    
    public void setScanResult(ScanResult scanResult) {
        this.scanResult = scanResult;
    }
    
    public boolean isComplete() {
        return scanResult.isComplete();
    }
    
    public ScanResult getScanResult() {
        return scanResult;
    }
}
