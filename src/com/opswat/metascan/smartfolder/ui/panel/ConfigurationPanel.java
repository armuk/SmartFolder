package com.opswat.metascan.smartfolder.ui.panel;

import com.opswat.metascan.smartfolder.ScanSession;
import com.opswat.metascan.smartfolder.settings.SettingsManager;
import com.opswat.metascan.smartfolder.ui.UserInterfaceManager;
import java.awt.Color;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 * Responsible for allowing the user to specify an API key, and choose whether 
 * or not to display the User Interface on startup.
 * 
 * @author Tristan Currens
 */
public class ConfigurationPanel extends javax.swing.JPanel {
    /**
     * The URL of the login page for Metascan Online.
     */
    private static final String METASCAN_SIGNUP_URL = "https://portal.opswat.com/metascan-online-api";
    
    private ArrayList<ScanSession> loggedSessions = new ArrayList();
    
    public ConfigurationPanel() {
        initComponents();
        
        logTable.getTableHeader().setReorderingAllowed(false);
    }
    
    public void setApiKeyFieldText(String text) {
        apiKeyField.setText(text);
    }
    
    public void setHideWindowOnStartup(boolean value) {
        hideWindowOnStartupCheckBox.setSelected(value);
    }
    
    /**
     * Pushes a new session to be logged by the SmartFolder application.
     * 
     * This will classify the file as "Unknown" by default, this value will be
     * updated as results are found
     * 
     * @param session 
     */
    public void pushSessionToLog(ScanSession session) {
        loggedSessions.add(session);
        
        DefaultTableModel dtm = (DefaultTableModel) logTable.getModel();
        dtm.addRow(new Object[] {
            session.getFile(),
            "Processing",
            ""
        });
        logTable.setModel(dtm);
    }
    
    /**
     * Updates the status of a <code>ScanSession</code>.
     * 
     * @param session the <code>ScanSession</code> in question
     */
    public void updateSessionStatus(ScanSession session, String status) {
        Integer rowID = loggedSessions.indexOf(session);
        
        if (rowID >= 0) {
            DefaultTableModel dtm = (DefaultTableModel) logTable.getModel();
            dtm.setValueAt(status, rowID, 1);
            logTable.setModel(dtm);
        }
    }
    
    /**
     * Updates the action taken on a <code>ScanSession</code>.
     * 
     * @param session the <code>ScanSession</code> in question
     */
    public void updateSessionAction(ScanSession session, String action) {
        Integer rowID = loggedSessions.indexOf(session);
        
        if (rowID >= 0) {
            DefaultTableModel dtm = (DefaultTableModel) logTable.getModel();
            dtm.setValueAt(action, rowID, 2);
            logTable.setModel(dtm);
        }
    }
    
    public void setAPIKeyFieldFlag(boolean value) {
        if (value) {
            apiKeyField.setBackground(new Color(255, 255, 209));
        } else {
            apiKeyField.setBackground(Color.WHITE);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        apiKeyLabel = new javax.swing.JLabel();
        apiKeyField = new javax.swing.JTextField();
        needAKeyLabel = new javax.swing.JLabel();
        hideWindowOnStartupCheckBox = new javax.swing.JCheckBox();
        logScroller = new javax.swing.JScrollPane();
        logTable = new javax.swing.JTable();
        submitButton = new javax.swing.JButton();
        asterisk = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(460, 270));
        setMinimumSize(new java.awt.Dimension(460, 270));
        setPreferredSize(new java.awt.Dimension(460, 270));

        apiKeyLabel.setText("API Key");

        needAKeyLabel.setForeground(new java.awt.Color(0, 0, 255));
        needAKeyLabel.setText("Need a Key?");
        needAKeyLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        needAKeyLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                needAKeyLabelMouseClicked(evt);
            }
        });

        hideWindowOnStartupCheckBox.setText("Hide Window on Startup");
        hideWindowOnStartupCheckBox.setFocusPainted(false);
        hideWindowOnStartupCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                hideWindowOnStartupCheckBoxItemStateChanged(evt);
            }
        });

        logTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "File", "Status", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        logTable.getTableHeader().setReorderingAllowed(false);
        logTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logTableMouseClicked(evt);
            }
        });
        logScroller.setViewportView(logTable);

        submitButton.setBackground(new java.awt.Color(41, 128, 185));
        submitButton.setText("Submit");
        submitButton.setFocusPainted(false);
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        asterisk.setForeground(new java.awt.Color(255, 0, 0));
        asterisk.setText("*");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(logScroller, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                    .addComponent(apiKeyField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                        .addComponent(apiKeyLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(asterisk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(needAKeyLabel))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                        .addComponent(hideWindowOnStartupCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(submitButton)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(apiKeyLabel)
                    .addComponent(needAKeyLabel)
                    .addComponent(asterisk))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(apiKeyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hideWindowOnStartupCheckBox)
                    .addComponent(submitButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(logScroller, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Fired when the user clicks on the text labeled "Need a Key?"
     * 
     * @param evt 
     */
    private void needAKeyLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_needAKeyLabelMouseClicked
        try {
            Desktop.getDesktop().browse(new URI(METASCAN_SIGNUP_URL));
        } catch (IOException | URISyntaxException e) {}
    }//GEN-LAST:event_needAKeyLabelMouseClicked

    private void hideWindowOnStartupCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_hideWindowOnStartupCheckBoxItemStateChanged
        SettingsManager.setHideMenuOnStartupEnabled(hideWindowOnStartupCheckBox.isSelected());
    }//GEN-LAST:event_hideWindowOnStartupCheckBoxItemStateChanged

    /**
     * Fired when the user has clicked on an entry on the log table.
     * 
     * If the user double clicks, their scan will be opened in the default browser.
     * 
     * @param evt 
     */
    private void logTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logTableMouseClicked
        if (evt.getClickCount() == 2) {
            int selectedRow = logTable.getSelectedRow();
            if (selectedRow >= 0) {
                ScanSession session = loggedSessions.get(logTable.getSelectedRow());
                if (session.getDataID() != null) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://www.metascan-online.com/en/scanresult/file/" + session.getDataID()));
                    } catch (URISyntaxException | IOException e) {
                    }
                }
            }
        }
    }//GEN-LAST:event_logTableMouseClicked

    /**
     * Fired when the user clicks the submit button.
     * 
     * @param evt 
     */
    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        SettingsManager.setApiKey(apiKeyField.getText());
        UserInterfaceManager.resetAPIKeyNotification();
    }//GEN-LAST:event_submitButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField apiKeyField;
    private javax.swing.JLabel apiKeyLabel;
    private javax.swing.JLabel asterisk;
    private javax.swing.JCheckBox hideWindowOnStartupCheckBox;
    private javax.swing.JScrollPane logScroller;
    private javax.swing.JTable logTable;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel needAKeyLabel;
    private javax.swing.JButton submitButton;
    // End of variables declaration//GEN-END:variables
}
