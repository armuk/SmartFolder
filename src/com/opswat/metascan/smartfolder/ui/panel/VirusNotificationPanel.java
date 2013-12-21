package com.opswat.metascan.smartfolder.ui.panel;

import com.opswat.metascan.smartfolder.ScanSession;
import com.opswat.metascan.smartfolder.ui.UserInterfaceManager;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import javax.swing.DefaultCellEditor;
import javax.swing.table.DefaultTableModel;

/**
 * Displays information to the user about which <code>File</code>s were determined
 * to be suspicious by the Metascan Online service.
 * 
 * The user will then be able to act on the displayed information by selecting
 * what action to take on each <code>File</code>
 *
 * @author Tristan Currens
 */
public class VirusNotificationPanel extends javax.swing.JPanel {
    private HashMap<String, ScanSession> pathToScanSession = new HashMap();
    
    /**
     * Default constructor.
     */
    public VirusNotificationPanel() {
        initComponents();
    }
    
    /**
     * Pushes a suspicious <code>File</code> to the User Interface.
     * 
     * If this <code>Panel</code> is not currently being displayed, the button
     * for this <code>Panel</code> will be flagged to notify the user.
     * 
     * @param session the <code>ScanSession</code> that was suspicious
     */
    public void pushVirusToTable(ScanSession session) {
        DefaultTableModel dtm = (DefaultTableModel) virusNotificationTable.getModel();
        pathToScanSession.put(session.getFile().getPath(), session);
        
        dtm.addRow(new Object[] {
            session.getFile().getName(),
            session.getFile().getParent(),
            "Delete"
        });
        
        if (!isVisible())
            UserInterfaceManager.getSmartFolderFrame().setVirusNotificationFlag(true);
        if (!UserInterfaceManager.getSmartFolderFrame().isVisible())
            UserInterfaceManager.notifyUserOfVirus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        virusNotificationTableActionCombo = new javax.swing.JComboBox();
        mainPanel = new javax.swing.JPanel();
        virusNotificationScroller = new javax.swing.JScrollPane();
        virusNotificationTable = new javax.swing.JTable();
        confirmButton = new javax.swing.JButton();
        deleteAllButton = new javax.swing.JButton();
        ignoreAllButton = new javax.swing.JButton();
        moreInformationButton = new javax.swing.JButton();

        virusNotificationTableActionCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Delete", "Ignore" }));

        setMaximumSize(new java.awt.Dimension(460, 270));
        setMinimumSize(new java.awt.Dimension(460, 270));
        setPreferredSize(new java.awt.Dimension(460, 270));

        virusNotificationTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "File Name", "File Location", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        virusNotificationTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        virusNotificationTable.getTableHeader().setReorderingAllowed(false);
        virusNotificationTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                virusNotificationTableMouseClicked(evt);
            }
        });
        virusNotificationScroller.setViewportView(virusNotificationTable);
        virusNotificationTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(virusNotificationTableActionCombo));

        confirmButton.setBackground(new java.awt.Color(41, 128, 185));
        confirmButton.setText("Confirm");
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmButtonActionPerformed(evt);
            }
        });

        deleteAllButton.setBackground(new java.awt.Color(41, 128, 185));
        deleteAllButton.setText("Delete All");
        deleteAllButton.setActionCommand("Delete");
        deleteAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleAllButtonActionPerformed(evt);
            }
        });

        ignoreAllButton.setBackground(new java.awt.Color(41, 128, 185));
        ignoreAllButton.setText("Ignore All");
        ignoreAllButton.setActionCommand("Ignore");
        ignoreAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleAllButtonActionPerformed(evt);
            }
        });

        moreInformationButton.setBackground(new java.awt.Color(41, 128, 185));
        moreInformationButton.setText("More Information");
        moreInformationButton.setFocusPainted(false);
        moreInformationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moreInformationButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(virusNotificationScroller, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(moreInformationButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ignoreAllButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteAllButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(confirmButton)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(virusNotificationScroller, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmButton)
                    .addComponent(deleteAllButton)
                    .addComponent(ignoreAllButton)
                    .addComponent(moreInformationButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Fired when the user selects the "Confirm" button.
     * 
     * This will go through the table and decide what action to take on each file.
     * If the user selected to delete the <code>File</code>, an attempt will be made
     * to delete the <code>File</code> from the FileSystem.
     * 
     * @param evt 
     */
    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmButtonActionPerformed
        DefaultTableModel dtm = (DefaultTableModel) virusNotificationTable.getModel();

        for (int i = dtm.getRowCount() - 1; i >= 0; i--) {
            String filePath = dtm.getValueAt(i,1) + "\\" + dtm.getValueAt(i, 0);
            ScanSession session = pathToScanSession.get(filePath);
            
            if (dtm.getValueAt(i, 2).equals("Delete")) {
                UserInterfaceManager.getSmartFolderFrame().getConfigurationPanel().updateSessionAction(session, "Deleted");
                new File(filePath).delete();
            } else {
                UserInterfaceManager.getSmartFolderFrame().getConfigurationPanel().updateSessionAction(session, "Ignored");
            }

            dtm.removeRow(i);
        }
        
        virusNotificationTable.setModel(dtm);
    }//GEN-LAST:event_confirmButtonActionPerformed

    /**
     * Fired when the user selects one of the toggle-all buttons.
     * 
     * This will set the value of every item in the table to the specified button's
     * <code>ActionCommand</code> ("Delete" or "Ignore")
     * 
     * @param evt 
     */
    private void toggleAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleAllButtonActionPerformed
        DefaultTableModel dtm = (DefaultTableModel) virusNotificationTable.getModel();

        for (int i = 0; i < dtm.getRowCount(); i++)
            dtm.setValueAt(evt.getActionCommand(), i, 2);

        virusNotificationTable.setModel(dtm);
    }//GEN-LAST:event_toggleAllButtonActionPerformed

    /**
     * Fired when the user selects the "More Information" button.
     * 
     * This will open a new browser tab with a link to the scan results page.
     * 
     * @param evt 
     */
    private void moreInformationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moreInformationButtonActionPerformed
        int selectedRow = virusNotificationTable.getSelectedRow();
        if (selectedRow >= 0) {
            DefaultTableModel dtm = (DefaultTableModel) virusNotificationTable.getModel();
            String path = dtm.getValueAt(selectedRow, 1) + "\\" + dtm.getValueAt(selectedRow, 0);
            ScanSession session = pathToScanSession.get(path);

            try {
                Desktop.getDesktop().browse(new URI("https://www.metascan-online.com/en/scanresult/file/" + session.getDataID()));
            } catch (URISyntaxException | IOException e) {
            }
        }
    }//GEN-LAST:event_moreInformationButtonActionPerformed

    /**
     * Fired when the user clicks on the virus notification table.
     * 
     * If the user double clicks, the "more information" handler will be fired.
     * 
     * @param evt 
     */
    private void virusNotificationTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_virusNotificationTableMouseClicked
        if (evt.getClickCount() == 2)
            moreInformationButtonActionPerformed(null);
    }//GEN-LAST:event_virusNotificationTableMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton confirmButton;
    private javax.swing.JButton deleteAllButton;
    private javax.swing.JButton ignoreAllButton;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton moreInformationButton;
    private javax.swing.JScrollPane virusNotificationScroller;
    private javax.swing.JTable virusNotificationTable;
    private javax.swing.JComboBox virusNotificationTableActionCombo;
    // End of variables declaration//GEN-END:variables
}
