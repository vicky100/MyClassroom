package myclassroom;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import server.Teacher;
import student.Student;

/****** @author vicky ******/

public class ItsMe extends javax.swing.JPanel {

    MainFrame mainFrame;
    String name;
    public boolean active;
    public boolean videoCall;
    public boolean voiceCall;
    BufferedImage image;

    private int seatNumber;
    private int typeOfObject; // #0 local, #1 remote
    
    VideoPanel videoPanel;
    
    public ItsMe(MainFrame mainFrame, int x, int y, int seatNumber) {
        initComponents();
        
        this.active = false;
        this.videoCall = false;
        this.voiceCall = false;
        image = null;
        this.mainFrame = mainFrame;
        
        this.seatNumber = seatNumber;
        this.typeOfObject = 1;
        
        videoPanel = new VideoPanel();
        videoPanel.setSize(183, 120);
        videoPanel.setLocation(8, 33);
        videoPanel.setVisible(true);
        this.add(videoPanel);
        
        this.setSize(new Dimension(200, 200));
        this.setLocation(x, y);
        this.setVisible(true);
    }

    
    public void registerThisOjbect() {
        this.typeOfObject = 0;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        myPictureLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        videoCallButton = new javax.swing.JButton();
        voiceCallButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(254, 254, 254));

        myPictureLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/maledisabled.png"))); // NOI18N

        nameLabel.setText("name");

        videoCallButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/videoCall1.png"))); // NOI18N
        videoCallButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                videoCallButtonActionPerformed(evt);
            }
        });

        voiceCallButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/voiceCall1.png"))); // NOI18N
        voiceCallButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                voiceCallButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(videoCallButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addComponent(voiceCallButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(myPictureLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(myPictureLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                    .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(videoCallButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(voiceCallButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void videoCallButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_videoCallButtonActionPerformed
        if(videoCall == false) {
            videoCallButton.setIcon(new ImageIcon("src/resources/videoCall4.png"));
            videoCall = true;        
            if(typeOfObject == 0) {
                if(seatNumber == 0)
                    Teacher.startVideoCalling();
                else
                    Student.startVideoCall();
            }
        }
        else if(videoCall == true) {
            videoCallButton.setIcon(new ImageIcon("src/resources/videoCall1.png"));
            videoCall = false;
        }
    }//GEN-LAST:event_videoCallButtonActionPerformed

    
    private void voiceCallButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_voiceCallButtonActionPerformed
        if(voiceCall == false) {
            voiceCallButton.setIcon(new ImageIcon("src/resources/voiceCall3.png"));
            voiceCall = true;
            if(typeOfObject == 0) {
                if(seatNumber == 0)
                    Teacher.startVoiceCalling();
                else
                    Student.startVoiceCall();
            }
        }
        else if(voiceCall == true) {
            voiceCallButton.setIcon(new ImageIcon("src/resources/voiceCall1.png"));
            voiceCall = false;
        }
    }//GEN-LAST:event_voiceCallButtonActionPerformed

    public void activate(String name) {
        this.active = true;
        this.name = name;
        myPictureLabel.setIcon(new ImageIcon("src/resources/maleenable.png"));
        System.out.println("image changed");
        nameLabel.setText(this.name);
        videoCallButton.setEnabled(true);
        voiceCallButton.setEnabled(true);
    }
    
    public void deactivate() {
        this.active = false;
        this.name = "username";
        myPictureLabel.setIcon(new ImageIcon("src/resources/maledisabled.png"));
        nameLabel.setText(this.name);
        videoCallButton.setEnabled(false);
        voiceCallButton.setEnabled(false);
    }
    
    public void setImage(BufferedImage image) {
        
        if(image == null) {
            System.out.println("no image received");
            return;
        }
        videoPanel.setImage(image);
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel myPictureLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton videoCallButton;
    private javax.swing.JButton voiceCallButton;
    // End of variables declaration//GEN-END:variables
}
