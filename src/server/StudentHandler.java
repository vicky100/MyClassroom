package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import message.Message;
import message.TextPacket;
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
    
    public StudentHandler(Socket studentSocket, Map<String, StudentHandler> studentMap, int seatNumber, MainFrame mainFrame) {
        
        this.studentSocket = studentSocket;
        this.studentMap = studentMap;
        this.seatNumber = seatNumber;
        this.mainFrame = mainFrame;
        
        InetAddress studentSocketIP = studentSocket.getInetAddress();
        int studentPort = studentSocket.getPort();
        System.out.println("request from " + studentSocketIP + ":" + studentPort);
        studentID = studentName = studentSocketIP+":"+studentPort;
        
        try {    
            in = new ObjectInputStream(new BufferedInputStream(studentSocket.getInputStream()));
            out = new ObjectOutputStream(new BufferedOutputStream(studentSocket.getOutputStream()));
        } catch (IOException ex) {
            
        }
    }
    
    @Override
    public void run() {
        Message res = null, req;
        
        req = new Message(seatNumber, -1, 1, studentID, new TextPacket("Give us your name"));
        try {
            out.writeObject(req);
            res = (Message)in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
        
        }
        studentName = res.getName();
        mainFrame.activate(studentName, seatNumber);
        
        while(true) {
            try {
                res = (Message)in.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                
            } 
            
            switch(res.getCode()) {
                case 0:
                            break;
                case 1:
                            Teacher.queue.add(res);
                            break;
                case 2: 
                            VoiceReceiver.queue.add(res);
                            break;
                case 3:
                            VideoReceiver.queue.add(res);
                            break;
            }
            
            Iterator<String> it = studentMap.keySet().iterator();
            
            while(it.hasNext()) {
                String key = it.next();
                if( !key.equals(studentID) && key != null && studentID != null) {
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
