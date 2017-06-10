package myclassroom;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/******** @author vicky ******/
public class VideoPanel extends javax.swing.JPanel {

    BufferedImage image = null;

    public VideoPanel() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 176, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 114, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public void setImage(BufferedImage image) {
        
        if(image == null) {
            System.out.println("no image received");
            return;
        }
        this.image=image;
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {     
        super.paintComponent(g);
        if(image == null) return;
        Image scaledImage=image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
        g.drawImage(scaledImage, 0, 0, this);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
