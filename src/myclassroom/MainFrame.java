package myclassroom;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

/****** @author vicky ******/

public class MainFrame extends javax.swing.JFrame {

    WhiteBoard whiteBoard;
    public ItsMe its[] = new ItsMe[50];
    
    public MainFrame() {
        initComponents();
        
        Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(dm);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        logoLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MyClassroom");
        setBackground(new java.awt.Color(254, 254, 254));
        setForeground(new java.awt.Color(254, 254, 254));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(logoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(250, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(474, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /****** @param args ******/
    
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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
        
        System.out.println("doubt is here");
    }
    
    
    public void addWhiteBoard() {
        whiteBoard = new WhiteBoard(this);
        this.add(whiteBoard);
        logoLabel.setIcon(new ImageIcon("src/resources/logo1.png"));
        
        //System.out.println(Thread.currentThread().getName());
    }
    
    
    public void addItsMe() {
        int width = this.getWidth();
        int x[] = {10, 220, width-420, width-210};
        int y[] = {75, 300, 525, 600};
        int k = 0;
        for(int i = 0; i < 3; i++) {            
            for(int j = 0; j < 4; j++) {
                its[k] = new ItsMe(this, x[j], y[i], k);
                this.add(its[k]);
                k++;
            }    
        }
    }
    
    
    public void activate(String name, int index) {
        its[index].activate(name);
    }
    
    public void deactivate(int index) {
        
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel logoLabel;
    // End of variables declaration//GEN-END:variables
}
