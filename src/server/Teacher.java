package server;

import java.util.ArrayList;
import message.Message;
import message.Utils;
import myclassroom.MainFrame;

/****** @author vicky ******/

public class Teacher implements Runnable{

    static ArrayList<Message> queue = new ArrayList<Message>();
    
    static int seatNumber;
    static String name;
    static String id;
    static MainFrame mainFrame;
    
    public Teacher(String name, int seatNumber, String id, MainFrame mainFrame) {
        Teacher.seatNumber = seatNumber;
        Teacher.name = name;
        Teacher.id = id;
        Teacher.mainFrame  = mainFrame;
        
        new Thread(new VideoReceiver(mainFrame)).start();
        new Thread(new VoiceReceiver(mainFrame)).start();
        
        mainFrame.activate(name, seatNumber);
        mainFrame.its[seatNumber].registerThisOjbect();
    }
    
    public static void startVideoCalling() {
        System.out.println("videocalling is starting");
        new Thread(new VideoTransmitter(name, seatNumber, id, mainFrame.its[seatNumber], mainFrame)).start();
    }
    
    public static void startVoiceCalling() {
        System.out.println("voicecalling is starting");
        new Thread(new VoiceTransmitter(name, seatNumber, id, mainFrame.its[seatNumber])).start();
    }
    
    
    @Override
    public void run() {
        while(true) {
            if(queue.isEmpty()) {
                Utils.sleep(10);
            }
            else {
                Message res = queue.get(0);
                queue.remove(res);
                
                int seatNumber = res.getSeatNumber(); 
            }
        }
    }
    
}
