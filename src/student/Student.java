package student;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import myclassroom.MainFrame;

/****** @author vicky ******/

public class Student {

    
    /*** student related information ***/
    static private String name;
    static private int seatNumber;
    static private String id;
    
    static private MainFrame mainFrame;
    static private Socket client = null;
 
    
    static ObjectOutputStream out;
    static ObjectInputStream in;
    
    
    
    public Student(MainFrame mainFrame, Socket client, String name, int seatNumber, String id) {
        Student.mainFrame = mainFrame;
        Student.client = client;
        Student.name = name;
        Student.seatNumber = seatNumber;
        Student.id = id;
        
        
        try {
            out = new ObjectOutputStream(new BufferedOutputStream(client.getOutputStream()));
            in = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
        } catch (IOException ex) {
            
        }
        
        
        // activate local and teacher seat number
        mainFrame.activate("Teacher", 0);
        mainFrame.activate(name, seatNumber);
        mainFrame.its[seatNumber].registerThisOjbect();
        
        
        new Thread(new VideoReceiver(mainFrame)).start();
        new Thread(new VoiceReceiver()).start();
        new Thread(new OverAllReceiver(in)).start();
    }
    
    public static void startVideoCall() {
        new Thread(new VideoTransmitter(name, seatNumber, id, mainFrame.its[seatNumber], out)).start();
    }
    
    public static void startVoiceCall() {
        new Thread(new VoiceTransmitter(name, seatNumber, id, mainFrame.its[seatNumber], out)).start();
    }
    
}
