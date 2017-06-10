package server;

import java.util.ArrayList;
import message.Message;
import message.Utils;
import myclassroom.MainFrame;

/****** @author vicky ******/

public class Teacher implements Runnable{

    static ArrayList<Message> queue = new ArrayList<Message>();
    
    static private int seatNumber;
    static private String name;
    static String id;
    static MainFrame mainFrame;
    
    public Teacher(String name, int seatNumber, String id, MainFrame mainFrame) {
        this.seatNumber = seatNumber;
        this.name = name;
        this.id = id;
        this.mainFrame  = mainFrame;
        
        new Thread(new VideoReceiver(mainFrame)).start();
        new Thread(new VoiceReceiver(mainFrame)).start();
        
        mainFrame.activate(name, seatNumber);
        mainFrame.its[seatNumber].registerThisOjbect();
    }
    
    public static void startVideoCalling() {
        new Thread(new VideoTransmitter(name, seatNumber, id, mainFrame.its[seatNumber])).start();
    }
    
    public static void startVoiceCalling() {
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
