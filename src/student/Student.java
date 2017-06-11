package student;

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
    
    
    public Student(MainFrame mainFrame, Socket client, String name, String teacherName, int seatNumber, String id, ObjectInputStream in, ObjectOutputStream out) {
        Student.mainFrame = mainFrame;
        Student.client = client;
        Student.name = name;
        Student.seatNumber = seatNumber;
        Student.id = id;
        this.in = in;
        this.out = out;
        
        
        // activate local and teacher seat number
        mainFrame.activate(teacherName, 0);
        mainFrame.activate(name, seatNumber);
        mainFrame.its[seatNumber].registerThisOjbect();
        
        
        new Thread(new VideoReceiver(mainFrame)).start();
        new Thread(new VoiceReceiver()).start();
        new Thread(new OverAllReceiver(in, mainFrame)).start();
    }
    
    public static void startVideoCall() {
        System.out.println("start videocalliing");
        new Thread(new VideoTransmitter(name, seatNumber, id, mainFrame.its[seatNumber], out, mainFrame)).start();
    }
    
    public static void startVoiceCall() {
        System.out.println("start voicecalliing");
        new Thread(new VoiceTransmitter(name, seatNumber, id, mainFrame.its[seatNumber], out)).start();
    }
    
}
