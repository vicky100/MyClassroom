package startup;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import message.Message;
import message.TextPacket;
import myclassroom.MainFrame;
import server.Server;
import student.Student;



public class MainPanel extends javax.swing.JPanel {

    BufferedImage image = null;
    StartUp startUp;
    
    public MainPanel(StartUp startUp) {
        initComponents();
        
        this.startUp = startUp;
        this.setSize(500, 500);
        this.setLocation(0, 0);
        try {
            image = ImageIO.read(new File("src/resources/fourth.jpg"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error in image loading in class startUp.MainPanel", "Imagepath Not Found", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        myName = new javax.swing.JTextField();
        joinClassIP = new javax.swing.JTextField();
        startClassPort = new javax.swing.JTextField();
        joinClassPort = new javax.swing.JTextField();
        loadingLabel = new javax.swing.JLabel();

        joinClassIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joinClassIPActionPerformed(evt);
            }
        });

        startClassPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startClassPortActionPerformed(evt);
            }
        });

        joinClassPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joinClassPortActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(startClassPort, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(joinClassIP, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(joinClassPort, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(197, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(loadingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(103, 103, 103))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(157, 157, 157)
                .addComponent(loadingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(myName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(joinClassIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(joinClassPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addComponent(startClassPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void startClassPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startClassPortActionPerformed
        
        if(Verifier.nameVerify(myName.getText()) == false) {
            JOptionPane.showMessageDialog(this, "Enter username accoding to this regex: \"^[a-zA-Z0-9_]{6,14}$\" ", "Incorrect Username", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        
        if(Verifier.portVarify(startClassPort.getText()) == false) {
            JOptionPane.showMessageDialog(this, "Port must be a number from 1025 to 65535", "Incorrect Port", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        MainFrame mainFrame = new MainFrame();
        mainFrame.addWhiteBoard();
        mainFrame.addItsMe();
        
        String name = Verifier.name;
        int port = Verifier.port;
        
        try {
            new Thread(new Server(port, name, mainFrame)).start();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error in binding port for server", "Server Error", JOptionPane.ERROR_MESSAGE);
        }
            
        mainFrame.setVisible(true);
        startUp.dispose();
    }//GEN-LAST:event_startClassPortActionPerformed

    public void joinClass() {
        Socket client = null;
        
        String name = myName.getText();
        
        if(Verifier.nameVerify(name) == false) {
            JOptionPane.showMessageDialog(this, "Enter username accoding to this regex: \"^[a-zA-Z0-9_]{6,14}$\" ", "Incorrect Username", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String str = joinClassPort.getText();
        
        if(Verifier.portVarify(str) == false) {
            JOptionPane.showMessageDialog(this, "Port must be a number from 1025 to 65535", "Incorrect Port", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        str = joinClassIP.getText();
        if(Verifier.ipAddressVarify(str) == false) {
            JOptionPane.showMessageDialog(this, "Enter the correct IP Address", "Incorrect IPAddress", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            client = new Socket(Verifier.ipAddress, Verifier.port);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error in socket connection to server", "Connection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        
        ObjectInputStream in;
        ObjectOutputStream out;
        
        try {
            in = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
            out = new ObjectOutputStream(new BufferedOutputStream(client.getOutputStream()));
            out.flush();
        }
        catch(IOException ex) {
            JOptionPane.showMessageDialog(this, "Error in obtaining input/output streams", "Stream creation", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Message res;
        try {
            res = (Message)in.readObject();
            TextPacket reqTP = new TextPacket("Pleasure to join the class");
            Message req = new Message(name, 1, 1, reqTP);
            out.writeObject(req);
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error in req/res message", "Error in read/write", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String teacherName = ((TextPacket)res.getData()).getData();
        JOptionPane.showMessageDialog(this, "#"+teacherName+": Welcome! "+name+", to join the class. Your seat number is: "+res.getSeatNumber() , "Server Reply" , JOptionPane.INFORMATION_MESSAGE);
        
        MainFrame mainFrame = new MainFrame();
        mainFrame.addWhiteBoard();
        mainFrame.addItsMe();
        Student student = new Student(mainFrame, client, name, teacherName, res.getSeatNumber(), res.getID(), in, out);
        mainFrame.setVisible(true);
        
        startUp.dispose();
    }
    
    
    private void joinClassPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_joinClassPortActionPerformed
        this.joinClass();
    }//GEN-LAST:event_joinClassPortActionPerformed

    private void joinClassIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_joinClassIPActionPerformed
        this.joinClass();
    }//GEN-LAST:event_joinClassIPActionPerformed

    @Override
    public void paintComponent(Graphics g) {     
        super.paintComponent(g);
        if(image == null) return;
        Image scaledImage=image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
        g.drawImage(scaledImage, 0, 0, this);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField joinClassIP;
    private javax.swing.JTextField joinClassPort;
    private javax.swing.JLabel loadingLabel;
    private javax.swing.JTextField myName;
    private javax.swing.JTextField startClassPort;
    // End of variables declaration//GEN-END:variables
}
