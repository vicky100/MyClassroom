package student;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import message.Message;
import message.Utils;
import message.VideoPacket;
import myclassroom.MainFrame;

/****** @author vicky ******/

public class VideoReceiver implements Runnable{

    static ArrayList<Message> queue = new ArrayList<Message>();
    
    MainFrame mainFrame;
    
    public VideoReceiver(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
    
    
    @Override
    public void run() {
        while(true) {
            if(queue.isEmpty()) {
                Utils.sleep(20);
                continue;
            }
            
            Message res = queue.get(0);
            queue.remove(res);
            int seatNumber = res.getSeatNumber();
            if(mainFrame.its[seatNumber].videoCall == true) {
                if(res.getData() instanceof VideoPacket) {
                    VideoPacket vp = (VideoPacket) res.getData();
                    ByteArrayInputStream bais = new ByteArrayInputStream(vp.getData());
                    
                    try {
                        BufferedImage image = ImageIO.read(bais);
                        if(image != null)
                            mainFrame.its[seatNumber].setImage(image);
                    } catch (IOException ex) {

                    }
                }    
            }
        }
    }
    
}
