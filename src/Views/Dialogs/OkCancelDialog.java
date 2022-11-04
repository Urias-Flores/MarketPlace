
package Views.Dialogs;

import Resourse.Utilities;
import javax.swing.Icon;
import javax.swing.ImageIcon;


public class OkCancelDialog extends javax.swing.JDialog {

    private int X, Y;
    private boolean value;
    
    private Icon iconDelete = new ImageIcon(getClass().getResource("/Icons/delete.png"));
    private Icon iconCancel = new ImageIcon(getClass().getResource("/Icons/cancel.png"));
    private Icon iconError = new ImageIcon(getClass().getResource("/Icons/error.png"));
    private Icon iconOK = new ImageIcon(getClass().getResource("/Icons/ok.png"));
    private Icon iconWarning = new ImageIcon(getClass().getResource("/Icons/warning.png"));
    
    public void setIcon(int Type)
    {
        switch(Type){
            case -3:
                lbIcon.setIcon(iconError);
                break;
            case -2:
                lbIcon.setIcon(iconWarning);
                break;
            case -1:
                lbIcon.setIcon(iconDelete);
                break;
            case 0:
                lbIcon.setIcon(iconCancel);
                break;
            case 1:
                lbIcon.setIcon(iconOK);
                break;
            default:
                lbIcon.setIcon(iconError);
                break;
        }
    }
    
    public void setMessage(String Message){
        this.lbMessage.setText(Message);
    }
    
    public boolean getValue(){
        return value;
    }

    public OkCancelDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txtClose.addMouseListener(Utilities.getMlButtonClose());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        pnBarra = new javax.swing.JPanel();
        txtClose = new javax.swing.JLabel();
        lbMessage = new javax.swing.JLabel();
        lbIcon = new javax.swing.JLabel();
        btnCheckIN = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 240, 240)));

        pnBarra.setBackground(new java.awt.Color(15, 88, 132));
        pnBarra.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnBarraMouseDragged(evt);
            }
        });
        pnBarra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnBarraMousePressed(evt);
            }
        });

        txtClose.setBackground(new java.awt.Color(15, 88, 132));
        txtClose.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/close.png"))); // NOI18N
        txtClose.setOpaque(true);
        txtClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCloseMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnBarraLayout = new javax.swing.GroupLayout(pnBarra);
        pnBarra.setLayout(pnBarraLayout);
        pnBarraLayout.setHorizontalGroup(
            pnBarraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnBarraLayout.createSequentialGroup()
                .addGap(0, 562, Short.MAX_VALUE)
                .addComponent(txtClose, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnBarraLayout.setVerticalGroup(
            pnBarraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtClose, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        lbMessage.setFont(new java.awt.Font("Roboto", 0, 20)); // NOI18N
        lbMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbMessage.setText("Descripcion");

        lbIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/error.png"))); // NOI18N

        btnCheckIN.setBackground(new java.awt.Color(49, 152, 65));
        btnCheckIN.setFont(new java.awt.Font("Roboto", 0, 20)); // NOI18N
        btnCheckIN.setForeground(new java.awt.Color(255, 255, 255));
        btnCheckIN.setText("Aceptar");
        btnCheckIN.setPreferredSize(new java.awt.Dimension(94, 32));
        btnCheckIN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckINActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(140, 40, 40));
        btnCancel.setFont(new java.awt.Font("Roboto", 0, 20)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("Cancelar");
        btnCancel.setPreferredSize(new java.awt.Dimension(94, 32));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lbIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6))
            .addComponent(pnBarra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnCheckIN, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnBarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbIcon, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbMessage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCheckIN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCloseMouseClicked
        this.dispose();
    }//GEN-LAST:event_txtCloseMouseClicked

    private void pnBarraMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnBarraMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - X, y - Y);
    }//GEN-LAST:event_pnBarraMouseDragged

    private void pnBarraMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnBarraMousePressed
        X = evt.getX();
        Y = evt.getY();
    }//GEN-LAST:event_pnBarraMousePressed

    private void btnCheckINActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckINActionPerformed
        value = true;
        setVisible(false);
    }//GEN-LAST:event_btnCheckINActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        value = false;
        setVisible(false);
    }//GEN-LAST:event_btnCancelActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OkCancelDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            OkCancelDialog dialog = new OkCancelDialog(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCheckIN;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbIcon;
    private javax.swing.JLabel lbMessage;
    private javax.swing.JPanel pnBarra;
    private javax.swing.JLabel txtClose;
    // End of variables declaration//GEN-END:variables
}
