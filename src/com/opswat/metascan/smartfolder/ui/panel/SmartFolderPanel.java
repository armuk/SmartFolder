package com.opswat.metascan.smartfolder.ui.panel;

import com.opswat.metascan.smartfolder.ScanManager;
import com.opswat.metascan.smartfolder.DirectoryMonitor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * Responsible for displaying information about which directories are being 
 * monitored by the system.
 *
 * @author Tristan Currens
 */
public class SmartFolderPanel extends javax.swing.JPanel {

    /**
     * Default constructor.
     */
    public SmartFolderPanel() {
        initComponents();
        
        smartFolderTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        initDragAndDrop();
    }
    
    /**
     * Sets the directories that are displayed in the <code>JTree</code>.
     * 
     * The specified list of directories will be sorted to determine the best way
     * to display them in tree form.
     * 
     * @param directories the directories currently being monitored.
     */
    public void updateMonitoredDirectoriesTree(ArrayList<File> directories) {
        ArrayList<File> unsortedDirectories = new ArrayList(directories);
        ArrayList<File> sortedDirectories   = new ArrayList();
        
        rescanSelectedButton.setEnabled(directories.size() > 0);
        rescanAllButton.setEnabled(directories.size() > 0);
        
        // sort the directory paths by ascending length. This sorted list will
        // be used to build the tree in the most consolidated way possible
        while (!unsortedDirectories.isEmpty()) {
            int lowIndex = 0;
            for (int i = 0; i < unsortedDirectories.size(); i++)
                if (unsortedDirectories.get(i).getPath().length() < unsortedDirectories.get(lowIndex).getPath().length())
                    lowIndex = i;
            sortedDirectories.add(unsortedDirectories.remove(lowIndex));
        }
        
        // the root node
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("SmartFolders");
        
        // go through the list of directories and build a tree of the directory
        // structure of the monitored directories
        while (!sortedDirectories.isEmpty()) {
            File directory = sortedDirectories.remove(0);
            DefaultMutableTreeNode dmtn = getDirectoryNode(directory, sortedDirectories);
            dmtn.setUserObject(directory);
            
            rootNode.add(dmtn);
        }
        
        // add the nodes to the tree
        smartFolderTree.setModel(new DefaultTreeModel(rootNode));
    }
    
    /**
     * Recursively gets the node for the specified directory.
     * 
     * This node will add the recursively defined child nodes for each directory
     * under the specified directory in the file system.
     * 
     * @param directory the directory for which a node should be built
     * @param otherDirectories a list of the remaining monitored directories
     * 
     * @return a node representation of the current directory with children representing
     * sub-directories
     */
    private DefaultMutableTreeNode getDirectoryNode(File directory, ArrayList<File> otherDirectories) {
        DefaultMutableTreeNode dmtn = new DefaultMutableTreeNode(directory.getName());
        
        // loop through the remaining directories and find those whose parent is
        // the specified directory. For each of these children, recursively build
        // their node. Add that node to this one
        for (int i = otherDirectories.size() - 1; i >= 0; i--)
            if (otherDirectories.get(i).getParent().equals(directory.getPath()))
                dmtn.add(getDirectoryNode(otherDirectories.remove(i), otherDirectories));
        
        return dmtn;
    }
    
    /**
     * Gets the <code>File</code> representation of the currently selected node
     * in the <code>JTree</code>.
     * 
     * @return the <code>File</code>; <code>null</code> if nothing is selected
     */
    private File getSelectedSmartFolder() {
        TreePath path = smartFolderTree.getSelectionPath();
        
        // build the path
        if (path != null && path.getPathCount() > 1) {
            String filePath = "";
            for (int i = 1; i < path.getPathCount(); i++)
               filePath += path.getPathComponent(i) + "\\";

            // get the file for the path
            return new File(filePath);
        }
        return null;    
    }
    
    /**
     * Gets the top level SmartFolders from the <code>JTree</code>.
     * 
     * The returned array omits any sub-directories, as those sub-directories are
     * inherently defined by the returned directories (as they are recursive-children)
     * of the returned values. 
     * 
     * Example:<br>
     * If the following directories are being monitored:<ul>
     * <li>C:\Users\TristanCurrens\SomeFolder</li>
     * <li>C:\Users\TristanCurrens\SomeFolder\F1</li>
     * <li>C:\Users\TristanCurrens\SomeFolder\F2</li>
     * <li>C:\Users\TristanCurrens\AnotherFolder</li>
     * <li>C:\Users\TristanCurrens\AnotherFolder\F1</li></ul>
     * 
     * This method will return <code>[C:\Users\TristanCurrens\SomeFolder, C:\Users\TristanCurrens\AnotherFolder]</code> 
     * because the other directories are able to be found using the file.listFiles()
     * method of <code>File</code>.
     * 
     * @return the top level directories
     */
    public File[] getTopLevelSmartFolders() {
        DefaultTreeModel dtm = (DefaultTreeModel) smartFolderTree.getModel();
        DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode) dtm.getRoot();

        File[] topLevelSmartFolders = new File[dmtn.getChildCount()];
        
        // get each child of the root node on the tree
        for (int i = 0; i < dmtn.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) dmtn.getChildAt(i);
            topLevelSmartFolders[i] = (File) child.getUserObject();
        }
        
        return topLevelSmartFolders;
    }
    
    /**
     * Initializes drag and drop.
     * 
     * Dragging a directory into the <code>JTree</code> will automatically register
     * the directory to be monitored.
     */
    private void initDragAndDrop() {
        smartFolderTree.setDropTarget(new DropTarget() {
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
                                if (source.isDirectory()) {
                                    DirectoryMonitor.registerDirectory(source);
                                }
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
        smartFolderTreeScroller = new javax.swing.JScrollPane();
        smartFolderTree = new javax.swing.JTree();
        monitorNewDirectoryButton = new javax.swing.JButton();
        rescanSelectedButton = new javax.swing.JButton();
        rescanAllButton = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(460, 270));
        setMinimumSize(new java.awt.Dimension(460, 270));
        setPreferredSize(new java.awt.Dimension(460, 270));

        mainPanel.setMinimumSize(new java.awt.Dimension(459, 273));

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("SmartFolders");
        smartFolderTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        smartFolderTree.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                smartFolderTreeKeyPressed(evt);
            }
        });
        smartFolderTreeScroller.setViewportView(smartFolderTree);

        monitorNewDirectoryButton.setBackground(new java.awt.Color(41, 128, 185));
        monitorNewDirectoryButton.setText("Monitor New Directory");
        monitorNewDirectoryButton.setFocusPainted(false);
        monitorNewDirectoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monitorNewDirectoryButtonActionPerformed(evt);
            }
        });

        rescanSelectedButton.setBackground(new java.awt.Color(41, 128, 185));
        rescanSelectedButton.setText("Rescan Selected");
        rescanSelectedButton.setEnabled(false);
        rescanSelectedButton.setFocusPainted(false);
        rescanSelectedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rescanSelectedButtonActionPerformed(evt);
            }
        });

        rescanAllButton.setBackground(new java.awt.Color(41, 128, 185));
        rescanAllButton.setText("Rescan All");
        rescanAllButton.setEnabled(false);
        rescanAllButton.setFocusPainted(false);
        rescanAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rescanAllButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(smartFolderTreeScroller)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addGap(0, 97, Short.MAX_VALUE)
                        .addComponent(rescanAllButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rescanSelectedButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(monitorNewDirectoryButton)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(smartFolderTreeScroller, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monitorNewDirectoryButton)
                    .addComponent(rescanSelectedButton)
                    .addComponent(rescanAllButton))
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
            .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 270, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Fired when the user presses a key while focusing the <code>JTree</code>. 
     * 
     * This method handles unregistration of directories when the user presses the
     * "Delete" key.
     * 
     * @param evt 
     */
    private void smartFolderTreeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_smartFolderTreeKeyPressed
        if (evt.getKeyCode() == 127) {  // "Delete" = 127
            File selectedSmartFolder = getSelectedSmartFolder();
            if (selectedSmartFolder != null)
                DirectoryMonitor.unregisterDirectory(selectedSmartFolder);
        }
    }//GEN-LAST:event_smartFolderTreeKeyPressed

    /**
     * Fired when the user clicks the "Monitor New Directory" button. 
     * 
     * Opens a <code>JFileChooser</code> to allow the user to specify which directory
     * to monitor.
     * 
     * @param evt 
     */
    private void monitorNewDirectoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monitorNewDirectoryButtonActionPerformed
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            DirectoryMonitor.registerDirectory(jfc.getSelectedFile());
    }//GEN-LAST:event_monitorNewDirectoryButtonActionPerformed

    /**
     * Fired when the user clicks the "Rescan Selected" button. 
     * 
     * Sends the specified <code>File</code> to the <code>ScanManager</code> this
     * will bypass any existing information about when the <code>File</code> was
     * last scanned.
     * 
     * @param evt 
     */
    private void rescanSelectedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rescanSelectedButtonActionPerformed
        File selectedSmartFolder = getSelectedSmartFolder();
        if (selectedSmartFolder != null)
            ScanManager.pushFileForUpload(selectedSmartFolder, true);
    }//GEN-LAST:event_rescanSelectedButtonActionPerformed

    /**
     * Fired when the user clicks the "Rescan All" button. 
     * 
     * Sends all of the currently monitored directories to the <code>ScanManager</code> this
     * will bypass any existing information about when the <code>File</code> was
     * last scanned.
     * 
     * @param evt 
     */
    private void rescanAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rescanAllButtonActionPerformed
        File[] topLevelSmartFolders = getTopLevelSmartFolders();
        for (File smartFolder : topLevelSmartFolders)
            ScanManager.pushDirectoryForUpload(smartFolder, true);
    }//GEN-LAST:event_rescanAllButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton monitorNewDirectoryButton;
    private javax.swing.JButton rescanAllButton;
    private javax.swing.JButton rescanSelectedButton;
    private javax.swing.JTree smartFolderTree;
    private javax.swing.JScrollPane smartFolderTreeScroller;
    // End of variables declaration//GEN-END:variables
}
