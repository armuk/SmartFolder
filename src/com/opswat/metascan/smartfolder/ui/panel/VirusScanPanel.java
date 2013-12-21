package com.opswat.metascan.smartfolder.ui.panel;

import com.opswat.metascan.smartfolder.ScanManager;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

/**
 * Allows the user to manually scan specified directories or <code>File</code>s.
 *
 * @author Tristan Currens
 */
public class VirusScanPanel extends javax.swing.JPanel { 
    public final ImageIcon UPLOAD_ICON   = new ImageIcon(this.getClass().getResource("/img/upload.png"));
    
    /**
     * Default constructor.
     */
    public VirusScanPanel() {
        initComponents();
        initDragAndDrop();
    }
    
    /**
     * Initializes drag and drop.
     * 
     * Dragging a directory or <code>File</code> into the labeled area will
     * force a scan of the item. If a directory is dropped, it will be recursively
     * scanned.
     */
    private void initDragAndDrop() {
        dragAndDropPanel.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent dtde) {
                try {
                    Transferable transfer = dtde.getTransferable();

                    if (transfer.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                        List objects = (List) transfer.getTransferData(DataFlavor.javaFileListFlavor);
                        for (Object object : objects) {
                            if (object instanceof File) {
                                File source = (File) object;
                                if (source.isDirectory())
                                    ScanManager.pushDirectoryForUpload(source, true);
                                else if (source.isFile())
                                    ScanManager.pushFileForUpload(source, true);
                            }
                        }
                    }
                } catch (IOException | UnsupportedFlavorException e) {
                } finally {
                    dtde.dropComplete(true);
                }
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        fileOrDirectoryLabel = new javax.swing.JLabel();
        fileOrDirectoryField = new javax.swing.JTextField();
        browseButton = new javax.swing.JButton();
        confirmButton = new javax.swing.JButton();
        dragAndDropPanel = new javax.swing.JPanel();
        dragAndDropLabel = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(460, 270));
        setMinimumSize(new java.awt.Dimension(460, 270));

        fileOrDirectoryLabel.setText("File or Directory:");

        browseButton.setBackground(new java.awt.Color(41, 128, 185));
        browseButton.setText("Browse...");
        browseButton.setFocusPainted(false);
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });

        confirmButton.setBackground(new java.awt.Color(41, 128, 185));
        confirmButton.setText("Confirm");
        confirmButton.setFocusPainted(false);
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmButtonActionPerformed(evt);
            }
        });

        dragAndDropPanel.setBackground(new java.awt.Color(204, 204, 204));
        dragAndDropPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        dragAndDropLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dragAndDropLabel.setIcon(UPLOAD_ICON);
        dragAndDropLabel.setText("Drag Files or Directories Here to Automatically Scan");
        dragAndDropLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        dragAndDropLabel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout dragAndDropPanelLayout = new javax.swing.GroupLayout(dragAndDropPanel);
        dragAndDropPanel.setLayout(dragAndDropPanelLayout);
        dragAndDropPanelLayout.setHorizontalGroup(
            dragAndDropPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dragAndDropLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
        );
        dragAndDropPanelLayout.setVerticalGroup(
            dragAndDropPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dragAndDropPanelLayout.createSequentialGroup()
                .addComponent(dragAndDropLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(fileOrDirectoryLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fileOrDirectoryField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(browseButton))
                    .addComponent(dragAndDropPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(browseButton)
                    .addComponent(fileOrDirectoryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileOrDirectoryLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(confirmButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169, Short.MAX_VALUE)
                .addComponent(dragAndDropPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Fired when the user presses the "Browse" button.
     * 
     * This will open a <code>JFileChooser</code> to allow the user to choose
     * what <code>File</code> or directory to scan.
     * 
     * @param evt 
     */
    private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            if (selectedFile != null)
                fileOrDirectoryField.setText(selectedFile.getPath());
        }
    }//GEN-LAST:event_browseButtonActionPerformed

    /**
     * Fired when the user presses the "Confirm" button.
     * 
     * This will send the specified <code>File</code> or directory to the 
     * <code>ScanManager</code> to be scanned.
     * 
     * @param evt 
     */
    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmButtonActionPerformed
        File file = new File(fileOrDirectoryField.getText());
        if (file.exists())
            if (file.isDirectory())
                ScanManager.pushDirectoryForUpload(file, true);
            if (file.isFile())
                ScanManager.pushFileForUpload(file, true);
        
        fileOrDirectoryField.setText("");
    }//GEN-LAST:event_confirmButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton;
    private javax.swing.JButton confirmButton;
    private javax.swing.JLabel dragAndDropLabel;
    private javax.swing.JPanel dragAndDropPanel;
    private javax.swing.JTextField fileOrDirectoryField;
    private javax.swing.JLabel fileOrDirectoryLabel;
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables
}