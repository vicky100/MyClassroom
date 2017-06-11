package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JOptionPane;
import message.Message;
import message.TextPacket;
import myclassroom.ItsMe;
import myclassroom.MainFrame;

/****** @author vicky ******/

public class StudentHandler implements Runnable{

    Socket studentSocket = null;
    String studentID;
    String studentName;
    int seatNumber;
    
    ObjectInputStream in;
    ObjectOutputStream out;
    
    Map<String, StudentHandler> studentMap;
    MainFrame mainFrame;
    
    public StudentHandler(Socket sSocket, Map<String, StudentHandler> sMap, int seatNo, MainFrame mainF) {
        
        studentSocket = sSocket;
        studentMap = sMap;
        seatNumber = seatNo;
        mainFrame = mainF;
    
        SocketAddress studentSocketIP = studentSocket.getRemoteSocketAddress();
        int studentPort = studentSocket.getPort();
        System.out.println("request from " + studentSocketIP.toString() + ":" + studentPort);
        studentID = studentName = studentSocketIP+":"+studentPort;
        
        try {
            out = new ObjectOutputStream(new BufferedOutputStream(studentSocket.getOutputStream()));
            out.flush();
            in = new ObjectInputStream(new BufferedInputStream(studentSocket.getInputStream()));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(mainFrame, "Error in creating student handler(constructor)", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void run() {
        Message res = null, req;
        
        req = new Message(seatNumber, -1, 1, studentID, new TextPacket(Teacher.name));
        try {
            out.writeObject(req);
            out.flush();
            res = (Message)in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
       
        }
        if(res != null) {
            studentName = res.getName();
            mainFrame.activate(studentName, seatNumber);
        }
        
        System.out.println("Now student "+ studentName + ": " +seatNumber +" is ready to boom boom");
        while(true) {
            
            try {
                Object obj = in.readObject();
                if(obj instanceof Message) {
                    res = (Message)obj; 
                    if(res != null) {
                        
                        
                        /*** uncomment the below line to see the status of incoming messages in terminal ***/
                        
                       // System.out.println("message received from #: " + res.getName() + ", code : " + res.getCode() + ", seatNumber : "+res.getSeatNumber());
                        
                        
                        
                        
                        ItsMe user = mainFrame.its[res.getSeatNumber()];
                        switch(res.getCode()) {
                            case 0:
                                        break;
                            case 1:
                                        Teacher.queue.add(res);
                                        break;
                            case 2: 
                                        if(user.voiceCall) {
                                            VoiceReceiver.queue.add(res);
                                        }
                                        break;
                            case 3:
                                        if(user.videoCall) {
                                            VideoReceiver.queue.add(res);
                                        }
                                        break;
                        }

                        Iterator<String> it = studentMap.keySet().iterator();

                        while(it.hasNext()) {
                            String key = it.next();
                            if(key != null && studentID != null) {
                                if( !key.equals(studentID)) {
                                    StudentHandler temp = studentMap.get(key);
                                    if(temp != null) {
                                        try {
                                            temp.out.writeObject(res);
                                        } catch (IOException ex) {

                                        }
                                    }
                                }
                            }    
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(mainFrame, "Error in casting Message object(StudentHandler)", "Casting error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
