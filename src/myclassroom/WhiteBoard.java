package myclassroom;

import java.awt.Dimension;
import java.awt.Point;

/****** @author vicky ******/

public class WhiteBoard extends javax.swing.JPanel {

    MainFrame mainFrame;
   
    public WhiteBoard(MainFrame mainFrame) {
        initComponents();
        
        this.mainFrame = mainFrame;
        this.setSize(new Dimension(500, 575));
        this.setLocation(mainFrame.getWidth()/2-this.getWidth()/2, 75);
        this.setVisible(true);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(254, 247, 247));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 575, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
