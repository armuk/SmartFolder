package com.opswat.metascan.smartfolder.ui.panel;

import com.opswat.metascan.smartfolder.devicedetection.DeviceDetectionManager;
import com.opswat.metascan.smartfolder.ScanManager;
import com.opswat.metascan.smartfolder.settings.SettingsManager;
import java.io.File;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

/**
 * Responsible for selecting whether or not to detect new devices that are added 
 * to the system and for showing them to the user for rescan as necessary.
 *
 * @author Tristan Currens
 */
public class DeviceDetectionPanel extends javax.swing.JPanel {

    public DeviceDetectionPanel() {
        initComponents();
        
        deviceTable.getTableHeader().setReorderingAllowed(false);
    }
    
    public void setAutoscanNewDevices(boolean value) {
        autoscanCheckBox.setSelected(value);
    }
    
    /**
     * Adds the specified device to the table. This will display information to 
     * the user regarding the ID of the drive, the name of the drive, and the 
     * size of the disk.
     * 
     * @param device the device to add
     */
    public void addDeviceToTable(File device) {
        DefaultTableModel dtm = (DefaultTableModel) deviceTable.getModel();
        
        FileSystemView fsv = FileSystemView.getFileSystemView();
        String name = fsv.getSystemDisplayName(device);
        long usableSpace = device.getUsableSpace();
        long totalSpace = device.getTotalSpace();
        
        dtm.addRow(new Object[]{device, name, usableSpace, totalSpace});
        
        deviceTable.setModel(dtm);
    }
    
    /**
     * Removes the specified device from the table.
     * 
     * @param device the device to remove
     */
    public void removeDeviceFromTable(File device) {
        DefaultTableModel dtm = (DefaultTableModel) deviceTable.getModel();
        
        // find and remove the device from the table
        for (int i = 0; i < dtm.getRowCount(); i++) {
            if (dtm.getValueAt(i, 0).equals(device.getName())) {
                dtm.removeRow(i);
                deviceTable.setModel(dtm);
                return;
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        autoscanCheckBox = new javax.swing.JCheckBox();
        tableScroller = new javax.swing.JScrollPane();
        deviceTable = new javax.swing.JTable();
        rescanAllButton = new javax.swing.JButton();
        rescanSelectedButton = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(460, 270));
        setMinimumSize(new java.awt.Dimension(460, 270));
        setPreferredSize(new java.awt.Dimension(460, 270));

        mainPanel.setMaximumSize(new java.awt.Dimension(460, 270));
        mainPanel.setMinimumSize(new java.awt.Dimension(460, 270));

        autoscanCheckBox.setText("Automatically Detect and Scan New Devices");
        autoscanCheckBox.setFocusPainted(false);
        autoscanCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                autoscanCheckBoxItemStateChanged(evt);
            }
        });

        tableScroller.setEnabled(false);

        deviceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Drive", "Name", "Usable Space (B)", "Total Space (B)"
            }
        ));
        deviceTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        deviceTable.getTableHeader().setReorderingAllowed(false);
        tableScroller.setViewportView(deviceTable);

        rescanAllButton.setBackground(new java.awt.Color(41, 128, 185));
        rescanAllButton.setText("Rescan All");
        rescanAllButton.setFocusPainted(false);
        rescanAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rescanAllButtonActionPerformed(evt);
            }
        });

        rescanSelectedButton.setBackground(new java.awt.Color(41, 128, 185));
        rescanSelectedButton.setText("Rescan Selected");
        rescanSelectedButton.setFocusPainted(false);
        rescanSelectedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rescanSelectedButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rescanAllButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rescanSelectedButton, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(tableScroller, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(11, 11, 11))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(autoscanCheckBox)
                        .addGap(0, 219, Short.MAX_VALUE))))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(autoscanCheckBox)
                .addGap(18, 18, 18)
                .addComponent(tableScroller, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rescanSelectedButton)
                    .addComponent(rescanAllButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Fired when the user toggles the "Automatically Detect and Scan New Devices"
     * check box.
     * 
     * This will remove all entries from the table if the check box is deselected
     * 
     * @param evt 
     */
    private void autoscanCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_autoscanCheckBoxItemStateChanged
        boolean autoscanEnabled = autoscanCheckBox.isSelected();
        
        if (!autoscanEnabled && DeviceDetectionManager.isRunning()) {
            DefaultTableModel dtm = (DefaultTableModel) deviceTable.getModel();
            dtm.setRowCount(0);
            deviceTable.setModel(dtm);
        }
        
        SettingsManager.setAutoScanNewDevicesEnabled(autoscanEnabled);
    }//GEN-LAST:event_autoscanCheckBoxItemStateChanged

    /**
     * Fired when the user clicks the "Rescan All" button. 
     * 
     * Sends all of the currently detected drives to the <code>ScanManager</code>. This
     * will bypass any existing information about when the drive was last scanned.
     * 
     * @param evt 
     */
    private void rescanAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rescanAllButtonActionPerformed
        DefaultTableModel dtm = (DefaultTableModel) deviceTable.getModel();
        
        for (int i = 0; i < dtm.getRowCount(); i++)
            ScanManager.pushDirectoryForUpload((File) dtm.getValueAt(i, 0), true);
    }//GEN-LAST:event_rescanAllButtonActionPerformed

    /**
     * Fired when the user clicks the "Rescan Selected" button. 
     * 
     * Sends the specified drive to the <code>ScanManager</code>. This
     * will bypass any existing information about when the drive was last scanned.
     * 
     * @param evt 
     */
    private void rescanSelectedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rescanSelectedButtonActionPerformed
        DefaultTableModel dtm = (DefaultTableModel) deviceTable.getModel();
        
        int selectedRow = deviceTable.getSelectedRow();
        
        if (selectedRow >= 0)
            ScanManager.pushDirectoryForUpload((File) dtm.getValueAt(selectedRow, 0), true);
    }//GEN-LAST:event_rescanSelectedButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox autoscanCheckBox;
    private javax.swing.JTable deviceTable;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton rescanAllButton;
    private javax.swing.JButton rescanSelectedButton;
    private javax.swing.JScrollPane tableScroller;
    // End of variables declaration//GEN-END:variables
}
