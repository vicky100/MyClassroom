package student;

import java.io.IOException;
import java.io.ObjectInputStream;
import message.Message;
import myclassroom.ItsMe;
import myclassroom.MainFrame;


/*******************************************************************************
* This thread/class is used to handle all the incoming message
* and sent them to their respective receiver thread/class
********************************************************************************/

/****** @author details ******
 * name: vicky
*****************************/
public class OverAllReceiver implements Runnable{

    
    ObjectInputStream in;
    MainFrame mainFrame;
    
    
    public OverAllReceiver(ObjectInputStream in, MainFrame mainFrame) {
        this.in = in;
        this.mainFrame = mainFrame;
    }
    
    
    @Override
    public void run() {
        while(true) {
  
            try {
                Object obj = in.readObject(); // ClassNotFound exception occur here
                if(obj instanceof Message) {
                    Message res = (Message) obj;
                    if(res != null) {
                        
                        /**** uncomment below line to see the status of incoming messages on terminal ****/
                        
                        // System.out.println("message receiver from #"+res.getName()+" : "+res.getCode());
                        
                        ItsMe temp = mainFrame.its[res.getSeatNumber()];
                        int code = res.getCode();
                        switch(code) {

                            case 0: // this is for others
                                    break;
                                
                            case 1: // this code is set to identify text message
                                    break;
                                
                            case 2: // this code is set to identify audioframes
                                    if(temp.voiceCall) {
                                        VoiceReceiver.queue.add(res);
                                    }
                                    break;
                                
                            case 3: // this code is set to identify videoframes
                                    if(temp.videoCall) {
                                        VideoReceiver.queue.add(res);
                                    }
                                    break;
                        }
                    }    
                } else {
                }
            
            } catch (IOException | ClassNotFoundException ex) {
                
            }          
            
        }
    }
    
}
