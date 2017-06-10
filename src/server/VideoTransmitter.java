package server;

import com.github.sarxos.webcam.Webcam;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import message.Message;
import message.VideoPacket;
import myclassroom.ItsMe;

/****** @author vicky ******/

public class VideoTransmitter implements Runnable{
    
    Webcam webcam;
    ItsMe itsMe;
    String name;
    int seatNumber;
    String id;
    
    public VideoTransmitter(String name, int seatNumber, String id, ItsMe itsMe) {
        this.name = name;
        this.seatNumber = seatNumber;
        this.itsMe = itsMe;
        this.id = id;
    }
    
    @Override
    public void run() {
        System.out.println("webcam is opening...");
        webcam = Webcam.getDefault();
        long startTime = System.currentTimeMillis();
        webcam.open();
        long endTime = System.currentTimeMillis();
        System.out.println("webcam " + webcam.getName() + " opened in " + (endTime-startTime) + " ms");

        while(itsMe.videoCall) {
            BufferedImage image = webcam.getImage();
            itsMe.setImage(image);
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            
            try {
                ImageIO.write(image, "jpg", baos);
                int size = 0;
                size = baos.size();
                VideoPacket vp = new VideoPacket(baos.toByteArray(), size);
                Message res = new Message(name, seatNumber, 1, 3, id, vp);
                
                Iterator<String> it = Server.studentMap.keySet().iterator();
            
                while(it.hasNext()) {
                    String key = it.next();
                    StudentHandler temp = Server.studentMap.get(key);
                    if(temp != null) {
                        try {
                            temp.out.writeObject(res);
                        } catch (IOException ex) {

                        }
                    }
                }
            } catch (IOException ex) {

            }
        }  
        System.out.println("webcam is closed");
        webcam.close();
    }
}
