package com.opswat.metascan.smartfolder.ui;

import com.opswat.metascan.smartfolder.ui.panel.ConfigurationPanel;
import com.opswat.metascan.smartfolder.ui.panel.VirusScanPanel;
import com.opswat.metascan.smartfolder.ui.panel.SmartFolderPanel;
import com.opswat.metascan.smartfolder.ui.panel.DeviceDetectionPanel;
import com.opswat.metascan.smartfolder.ui.panel.VirusNotificationPanel;
import java.awt.Color;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

/**
 * The default User Interface window that the user will see.
 * 
 * This interface will allow the user to make changes to the way that the program
 * functions. This includes allowing the user to select folders to monitor, turn
 * on device detection, and scan <code>File</code>s individually.
 * 
 * @author Tristan Currens
 */
public class SmartFolderFrame extends javax.swing.JFrame {  

    public final ImageIcon OPSWAT_LOGO                           = new ImageIcon(this.getClass().getResource("/img/opswat_logo.png"));
    public final ImageIcon OPSWAT_LOGO_64                        = new ImageIcon(this.getClass().getResource("/img/opswat_logo_64.png"));
    public final ImageIcon SMART_FOLDER_BUTTON_ICON              = new ImageIcon(this.getClass().getResource("/img/smart_folder.png"));
    public final ImageIcon VIRUS_SCAN_BUTTON_ICON                = new ImageIcon(this.getClass().getResource("/img/virus_scan.png"));
    public final ImageIcon VIRUS_NOTIFICATON_NONE_BUTTON_ICON    = new ImageIcon(this.getClass().getResource("/img/shield_normal.png"));
    public final ImageIcon VIRUS_NOTIFICATON_ALERT_BUTTON_ICON   = new ImageIcon(this.getClass().getResource("/img/shield_alert.png"));
    public final ImageIcon DEVICE_DETECTION_BUTTON_ICON          = new ImageIcon(this.getClass().getResource("/img/device_detection.png"));
    public final ImageIcon CONFIGURATION_BUTTON_ICON             = new ImageIcon(this.getClass().getResource("/img/configuration.png"));
   
    /**
     * Default constructor.
     */
    public SmartFolderFrame() {
        initComponents();
        
        controlToggleButtonStateChanged(null);
    }
    
    public ConfigurationPanel getConfigurationPanel() {
        return configurationPanel;
    }
    
    public DeviceDetectionPanel getDeviceDetectionPanel() {
        return deviceDetectionPanel;
    }
    
    public VirusScanPanel getVirusScanPanel() {
        return virusScanPanel;
    }
    
    public VirusNotificationPanel getVirusNotificationPanel() {
        return virusNotificationPanel;
    }
    
    public SmartFolderPanel getSmartFolderPanel() {
        return smartFolderPanel;
    }
    
    /**
     * Flags the <code>SmartFolderFrame</code> to tell the user that a virus has
     * been detected.
     * 
     * If <code>true</code>, this will change the icon of the <code>VirusNotificationPanel</code> 
     * button as well as outlining the button in yellow.
     * 
     * If <code>false</code>, this will restore the icon and original color of the 
     * <code>VirusNotificationPanel</code> button.
     * 
     * @param flag 
     */
    public void setVirusNotificationFlag(boolean flag) {
        if (flag) {
            virusNotificationToggleButton.setBackground(Color.YELLOW);
            virusNotificationToggleButton.setIcon(VIRUS_NOTIFICATON_ALERT_BUTTON_ICON);
        } else {
            virusNotificationToggleButton.setBackground(new Color(240, 240, 240));
            virusNotificationToggleButton.setIcon(VIRUS_NOTIFICATON_NONE_BUTTON_ICON);
        }
    }
    
    /**
     * Locks the window in a state that indicates to the user that an API key is
     * still needed.
     * 
     * @param value 
     */
    public void setAPIKeyLock(boolean value) {
        if (value) {
            swapToConfigurationView();
            
            virusNotificationToggleButton.setEnabled(false);
            virusScanToggleButton.setEnabled(false);
            deviceDetectionToggleButton.setEnabled(false);
            smartFolderToggleButton.setEnabled(false);
        } else {
            virusNotificationToggleButton.setEnabled(true);
            virusScanToggleButton.setEnabled(true);
            deviceDetectionToggleButton.setEnabled(true);
            smartFolderToggleButton.setEnabled(true);
        }
        
        configurationPanel.setAPIKeyFieldFlag(value);
    }
    
    /**
     * Sets the view to the default view (the virus notification panel)
     */
    public void swapToDefaultView() {
        virusNotificationToggleButton.setSelected(true);
        controlToggleButtonStateChanged(null);
        
        validate();
    }
    
    /**
     * Sets the view to the settings view (the configuration panel)
     */
    public void swapToConfigurationView() {
        configurationToggleButton.setSelected(true);
        controlToggleButtonStateChanged(null);
        
        validate();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        controlPanelButtonGroup = new javax.swing.ButtonGroup();
        backgroundPanel = new javax.swing.JPanel();
        topPanel = new javax.swing.JPanel();
        opswatLogo = new javax.swing.JLabel();
        bottomPanel = new javax.swing.JPanel();
        displayPanel = new javax.swing.JPanel();
        displayTitlePanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        contentPanel = new javax.swing.JPanel();
        contentLayeredPane = new javax.swing.JLayeredPane();
        deviceDetectionPanel = new com.opswat.metascan.smartfolder.ui.panel.DeviceDetectionPanel();
        smartFolderPanel = new com.opswat.metascan.smartfolder.ui.panel.SmartFolderPanel();
        virusNotificationPanel = new com.opswat.metascan.smartfolder.ui.panel.VirusNotificationPanel();
        configurationPanel = new com.opswat.metascan.smartfolder.ui.panel.ConfigurationPanel();
        virusScanPanel = new com.opswat.metascan.smartfolder.ui.panel.VirusScanPanel();
        controlPanel = new javax.swing.JPanel();
        virusNotificationToggleButton = new javax.swing.JToggleButton();
        smartFolderToggleButton = new javax.swing.JToggleButton();
        virusScanToggleButton = new javax.swing.JToggleButton();
        deviceDetectionToggleButton = new javax.swing.JToggleButton();
        configurationToggleButton = new javax.swing.JToggleButton();

        setTitle("Metascan Online SmartFolder");
        setIconImage(OPSWAT_LOGO_64.getImage());
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
        });

        backgroundPanel.setBackground(new java.awt.Color(28, 161, 220));

        topPanel.setBackground(new java.awt.Color(255, 255, 255));

        opswatLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        opswatLogo.setIcon(OPSWAT_LOGO);
        opswatLogo.setText(" ");
        opswatLogo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        opswatLogo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                opswatLogoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(opswatLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(opswatLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
        );

        bottomPanel.setBackground(new java.awt.Color(52, 152, 219));

        displayPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(52, 152, 219));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Virus Notifications");

        javax.swing.GroupLayout displayTitlePanelLayout = new javax.swing.GroupLayout(displayTitlePanel);
        displayTitlePanel.setLayout(displayTitlePanelLayout);
        displayTitlePanelLayout.setHorizontalGroup(
            displayTitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
        );
        displayTitlePanelLayout.setVerticalGroup(
            displayTitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displayTitlePanelLayout.createSequentialGroup()
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        contentPanel.setBackground(new java.awt.Color(204, 204, 204));

        deviceDetectionPanel.setBounds(0, 0, 460, 270);
        contentLayeredPane.add(deviceDetectionPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        smartFolderPanel.setBounds(0, 0, 460, 270);
        contentLayeredPane.add(smartFolderPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        virusNotificationPanel.setBounds(0, 0, 460, 270);
        contentLayeredPane.add(virusNotificationPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        configurationPanel.setBounds(0, 0, 460, 270);
        contentLayeredPane.add(configurationPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        virusScanPanel.setBounds(0, 0, 460, 270);
        contentLayeredPane.add(virusScanPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentLayeredPane)
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentLayeredPane)
        );

        javax.swing.GroupLayout displayPanelLayout = new javax.swing.GroupLayout(displayPanel);
        displayPanel.setLayout(displayPanelLayout);
        displayPanelLayout.setHorizontalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(displayTitlePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        displayPanelLayout.setVerticalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displayPanelLayout.createSequentialGroup()
                .addComponent(displayTitlePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        controlPanelButtonGroup.add(virusNotificationToggleButton);
        virusNotificationToggleButton.setIcon(VIRUS_NOTIFICATON_NONE_BUTTON_ICON);
        virusNotificationToggleButton.setSelected(true);
        virusNotificationToggleButton.setFocusPainted(false);
        virusNotificationToggleButton.setName("Virus Notifications"); // NOI18N
        virusNotificationToggleButton.setOpaque(true);
        virusNotificationToggleButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                controlToggleButtonStateChanged(evt);
            }
        });

        controlPanelButtonGroup.add(smartFolderToggleButton);
        smartFolderToggleButton.setIcon(SMART_FOLDER_BUTTON_ICON);
        smartFolderToggleButton.setFocusPainted(false);
        smartFolderToggleButton.setName("SmartFolder Configuration"); // NOI18N
        smartFolderToggleButton.setOpaque(true);
        smartFolderToggleButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                controlToggleButtonStateChanged(evt);
            }
        });

        controlPanelButtonGroup.add(virusScanToggleButton);
        virusScanToggleButton.setIcon(VIRUS_SCAN_BUTTON_ICON);
        virusScanToggleButton.setFocusPainted(false);
        virusScanToggleButton.setName("Metascan Online Virus Scan"); // NOI18N
        virusScanToggleButton.setOpaque(true);
        virusScanToggleButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                controlToggleButtonStateChanged(evt);
            }
        });

        controlPanelButtonGroup.add(deviceDetectionToggleButton);
        deviceDetectionToggleButton.setIcon(DEVICE_DETECTION_BUTTON_ICON);
        deviceDetectionToggleButton.setFocusPainted(false);
        deviceDetectionToggleButton.setName("Device Detection Configuration"); // NOI18N
        deviceDetectionToggleButton.setOpaque(true);
        deviceDetectionToggleButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                controlToggleButtonStateChanged(evt);
            }
        });

        controlPanelButtonGroup.add(configurationToggleButton);
        configurationToggleButton.setIcon(CONFIGURATION_BUTTON_ICON);
        configurationToggleButton.setFocusPainted(false);
        configurationToggleButton.setName("Configuration and Logging"); // NOI18N
        configurationToggleButton.setOpaque(true);
        configurationToggleButton.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                controlToggleButtonStateChanged(evt);
            }
        });

        javax.swing.GroupLayout controlPanelLayout = new javax.swing.GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deviceDetectionToggleButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addComponent(configurationToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(virusScanToggleButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(smartFolderToggleButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(virusNotificationToggleButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        controlPanelLayout.setVerticalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addComponent(virusNotificationToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(smartFolderToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(virusScanToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(deviceDetectionToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(configurationToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout bottomPanelLayout = new javax.swing.GroupLayout(bottomPanel);
        bottomPanel.setLayout(bottomPanelLayout);
        bottomPanelLayout.setHorizontalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bottomPanelLayout.createSequentialGroup()
                .addComponent(controlPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(displayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        bottomPanelLayout.setVerticalGroup(
            bottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(displayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(controlPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout backgroundPanelLayout = new javax.swing.GroupLayout(backgroundPanel);
        backgroundPanel.setLayout(backgroundPanelLayout);
        backgroundPanelLayout.setHorizontalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(topPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(bottomPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        backgroundPanelLayout.setVerticalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bottomPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Fired when the user clicks on the OPSWAT logo at the top of the <code>Frame</code>.
     * 
     * This will open the OPSWAT website in the user's default browser.
     * 
     * @param evt 
     */
    private void opswatLogoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_opswatLogoMouseClicked
        try {
            Desktop.getDesktop().browse(new URI("http://www.opswat.com/"));
        } catch (IOException | URISyntaxException e) {}
    }//GEN-LAST:event_opswatLogoMouseClicked
    /**
     * Fired when the user selects a new display mode from the left-side panel.
     * 
     * This will hide all of the <code>Panel</code>s that are being held in the
     * window and will selectively display the <code>Panel</code> that was selected.
     * 
     * @param evt
     */
    private void controlToggleButtonStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_controlToggleButtonStateChanged
        // hide all panels
        virusNotificationPanel.setVisible(false);
        smartFolderPanel.setVisible(false);
        virusScanPanel.setVisible(false);
        deviceDetectionPanel.setVisible(false);
        configurationPanel.setVisible(false);
        
        // choose the selected button
        JToggleButton selectedButton = 
                virusNotificationToggleButton.isSelected() ? virusNotificationToggleButton :
                smartFolderToggleButton.isSelected() ? smartFolderToggleButton :
                virusScanToggleButton.isSelected() ? virusScanToggleButton :
                deviceDetectionToggleButton.isSelected() ? deviceDetectionToggleButton:
                configurationToggleButton.isSelected() ? configurationToggleButton : null;
        
        // set the title of the panel
        if (selectedButton != null) {
            titleLabel.setText(selectedButton.getName());
            
            // choose the selected panel
            switch(selectedButton.getName()) {
                case "Virus Notifications":
                    virusNotificationPanel.setVisible(true);
                    setVirusNotificationFlag(false);
                    break;
                case "SmartFolder Configuration":
                    smartFolderPanel.setVisible(true);
                    break;
                case "Metascan Online Virus Scan":
                    virusScanPanel.setVisible(true);
                    break;
                case "Device Detection Configuration":
                    deviceDetectionPanel.setVisible(true);
                    break;
                case "Configuration and Logging":
                    configurationPanel.setVisible(true);
                    break;
            }
        }
    }//GEN-LAST:event_controlToggleButtonStateChanged

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        if (!this.isVisible())
            UserInterfaceManager.notifyOfBackground();
    }//GEN-LAST:event_formWindowDeactivated

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JPanel bottomPanel;
    private com.opswat.metascan.smartfolder.ui.panel.ConfigurationPanel configurationPanel;
    private javax.swing.JToggleButton configurationToggleButton;
    private javax.swing.JLayeredPane contentLayeredPane;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JPanel controlPanel;
    private javax.swing.ButtonGroup controlPanelButtonGroup;
    private com.opswat.metascan.smartfolder.ui.panel.DeviceDetectionPanel deviceDetectionPanel;
    private javax.swing.JToggleButton deviceDetectionToggleButton;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JPanel displayTitlePanel;
    private javax.swing.JLabel opswatLogo;
    private com.opswat.metascan.smartfolder.ui.panel.SmartFolderPanel smartFolderPanel;
    private javax.swing.JToggleButton smartFolderToggleButton;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel topPanel;
    private com.opswat.metascan.smartfolder.ui.panel.VirusNotificationPanel virusNotificationPanel;
    private javax.swing.JToggleButton virusNotificationToggleButton;
    private com.opswat.metascan.smartfolder.ui.panel.VirusScanPanel virusScanPanel;
    private javax.swing.JToggleButton virusScanToggleButton;
    // End of variables declaration//GEN-END:variables

}
