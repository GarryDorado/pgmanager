package com.supertel.pgmanager;

import com.supertel.pgmanager.process.ProcessReturnValues;
import com.supertel.pgmanager.utils.PgCtl;
import com.supertel.pgmanager.utils.PgDump;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author Grigoriy
 */
public class PgManagerJFrame extends javax.swing.JFrame {

    private static final Logger LOG = Logger.getLogger(PgManagerJFrame.class.getName());
    private static ImageIcon NOT_WORKING = new ImageIcon(PgManagerJFrame.class.getResource("/major.gif"));
    private static ImageIcon WORKING = new ImageIcon(PgManagerJFrame.class.getResource("/norm.gif"));
    private Boolean status = null;

    /** Creates new form PgmanagerJFrame */
    public PgManagerJFrame() {
        Settings settings = Settings.getInstance();

        try {
            settings.load();
        } catch (IOException ioe) {
            settings.setUserLabel("PostgreSQL");
            settings.setPort(5432);
            settings.setUserName("postgres");
            ioe.printStackTrace();
        }

        if (settings.getPathBin() == null || settings.getPathBin().isEmpty()) {
            settingsButtonActionPerformed(null);
        }

        initComponents();
        startButton.setEnabled(false);
        stopButton.setEnabled(false);
        restartButton.setEnabled(false);
        settingsButton.setEnabled(false);
        dumpButton.setEnabled(false);
        baseBackupButton.setEnabled(false);
        //
        startPgStatusTimer();
    }

    private void startPgStatusTimer() {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean newStatus = new PgCtl().status();
                    if (status == null || newStatus != status) {
                        status = newStatus;
                        if (status) {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    statusLabel.setIcon(WORKING);
                                    statusLabel.setText("Working");
                                    //
                                    startButton.setEnabled(false);
                                    settingsButton.setEnabled(false);
                                    stopButton.setEnabled(true);
                                    restartButton.setEnabled(true);
                                    dumpButton.setEnabled(true);
                                    baseBackupButton.setEnabled(true);
                                }
                            });
                        } else {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    statusLabel.setIcon(NOT_WORKING);
                                    statusLabel.setText("Not working");
                                    //
                                    startButton.setEnabled(true);
                                    settingsButton.setEnabled(true);
                                    stopButton.setEnabled(false);
                                    restartButton.setEnabled(false);
                                    dumpButton.setEnabled(false);
                                    baseBackupButton.setEnabled(false);
                                }
                            });
                        }
                    }
                } catch (IOException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }
        });
        timer.start();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        statusLabel = new javax.swing.JLabel();
        settingsButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        inputTextArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        restartButton = new javax.swing.JButton();
        startButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        dumpButton = new javax.swing.JButton();
        baseBackupButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/postgres.png"))); // NOI18N

        statusLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/major.gif"))); // NOI18N
        statusLabel.setText("Not working");

        settingsButton.setText("Settings");
        settingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsButtonActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Output"));

        inputTextArea.setEditable(false);
        inputTextArea.setColumns(20);
        inputTextArea.setRows(5);
        jScrollPane1.setViewportView(inputTextArea);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("PostgreSQL Manager");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Server management"));

        restartButton.setText("Restart");
        restartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restartButtonActionPerformed(evt);
            }
        });

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        stopButton.setText("Stop");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(restartButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stopButton)
                .addContainerGap(259, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {restartButton, startButton, stopButton});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(restartButton)
                    .addComponent(startButton)
                    .addComponent(stopButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Backup"));

        dumpButton.setText("Dump");
        dumpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dumpButtonActionPerformed(evt);
            }
        });

        baseBackupButton.setText("Backup");
        baseBackupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                baseBackupButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dumpButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(baseBackupButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {baseBackupButton, dumpButton});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dumpButton)
                    .addComponent(baseBackupButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(statusLabel)
                            .addComponent(settingsButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(statusLabel)
                        .addGap(18, 18, 18)
                        .addComponent(settingsButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        try {
            ProcessReturnValues prv = new PgCtl().stop();
            inputTextArea.setText(prv.getInput());
            if (!prv.getError().isEmpty()) {
                inputTextArea.append("--- Error\n");
                inputTextArea.append(prv.getError());
            }
        } catch (Exception ex) {
            inputTextArea.append(ex.getMessage());
            LOG.log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_stopButtonActionPerformed

    private void settingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsButtonActionPerformed
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SettingsDialog dialog = new SettingsDialog(new javax.swing.JFrame(), true);
                dialog.setVisible(true);
            }
        });
    }//GEN-LAST:event_settingsButtonActionPerformed

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        try {
            ProcessReturnValues prv = new PgCtl().start();
            inputTextArea.setText(prv.getInput());
            if (!prv.getError().isEmpty()) {
                inputTextArea.append("--- Error\n");
                inputTextArea.append(prv.getError());
            }
        } catch (Exception ex) {
            inputTextArea.append(ex.getMessage());
            LOG.log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_startButtonActionPerformed

    private void restartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restartButtonActionPerformed
        try {
            ProcessReturnValues prv = new PgCtl().restart();
            inputTextArea.setText(prv.getInput());
            if (!prv.getError().isEmpty()) {
                inputTextArea.append("--- Error\n");
                inputTextArea.append(prv.getError());
            }
        } catch (Exception ex) {
            inputTextArea.append(ex.getMessage());
            LOG.log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_restartButtonActionPerformed

    private void dumpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dumpButtonActionPerformed
        String dumpPath = chooseFileDialog("Create dump", "R:\\Temp\\", ".dump.zip");
        if (dumpPath != null && !dumpPath.isEmpty()) {
            try {
                ProcessReturnValues prv = new PgDump().dump(dumpPath);
                inputTextArea.setText(prv.getInput());
            } catch (Exception ex) {
                inputTextArea.append(ex.getMessage());
                LOG.log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_dumpButtonActionPerformed

    private void baseBackupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_baseBackupButtonActionPerformed
        String dumpPath = chooseFileDialog("Create base backup", "R:\\Temp\\", null);
        if (dumpPath != null && !dumpPath.isEmpty()) {
            try {
                ProcessReturnValues prv = new PgDump().baseBackup(dumpPath);
                inputTextArea.setText(prv.getInput());
            } catch (Exception ex) {
                inputTextArea.append(ex.getMessage());
                LOG.log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_baseBackupButtonActionPerformed

    private String chooseFileDialog(String title, String path, String fileExt) {
        JFileChooser fileChooser = new JFileChooser(title);
        fileChooser.setCurrentDirectory(new File(path));
        // Формирование имени по-умолчанию
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");
        String defauilFileName = formatter.format(new Date());
        if (fileExt != null) {
            defauilFileName = defauilFileName + fileExt;
        }
        fileChooser.setSelectedFile(new File(defauilFileName));
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        } else {
            return null;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PgManagerJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PgManagerJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PgManagerJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PgManagerJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PgManagerJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton baseBackupButton;
    private javax.swing.JButton dumpButton;
    private javax.swing.JTextArea inputTextArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton restartButton;
    private javax.swing.JButton settingsButton;
    private javax.swing.JButton startButton;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JButton stopButton;
    // End of variables declaration//GEN-END:variables
}
