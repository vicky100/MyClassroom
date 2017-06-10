package student;

import java.io.IOException;
import java.io.ObjectInputStream;
import message.Message;


/*******************************************************************************
* This thread/class is used to handle all the incoming message
* and sent them to their respective receiver thread/class
********************************************************************************/

/****** @author details ******
 * name: vicky
*****************************/
public class OverAllReceiver implements Runnable{

    
    ObjectInputStream in;
    
    
    public OverAllReceiver(ObjectInputStream in) {
        this.in = in;
    }
    
    
    @Override
    public void run() {
        while(true) {
            Message res = null;
            try {
                res = (Message) in.readObject();
            } catch (IOException | ClassNotFoundException ex) {
                
            }
            if(res != null) {
                int type = res.getCode();
                switch(type) {
                    
                    case 0: // this is for others
                                break;
                                
                    case 1: // this code is set to identify text message
                                break;
                                
                    case 2: // this code is set to identify audioframes
                                VoiceReceiver.queue.add(res);
                                break;
              
                    case 3: // this code is set to identify videoframes
                                VideoReceiver.queue.add(res);
                                break;
                }
            }    
        }
    }
    
}
