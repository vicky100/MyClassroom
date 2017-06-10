package student;

import com.github.sarxos.webcam.Webcam;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.imageio.ImageIO;
import message.Message;
import message.VideoPacket;
import myclassroom.ItsMe;

/****** @author vicky ******/

public class VideoTransmitter implements Runnable{
    
    Webcam webcam;
    ItsMe itsMe;
    ObjectOutputStream out;
    String name;
    int seatNumber;
    String id;
        
    public VideoTransmitter(String name, int seatNumber, String id, ItsMe itsMe, ObjectOutputStream out) {
        this.name = name;
        this.seatNumber = seatNumber;
        this.id = id;
        this.itsMe = itsMe;
        this.out = out;
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
                Message msg = new Message(name, seatNumber, 1, 3, id, vp);
                out.writeObject(msg);
                out.flush();
            } catch (IOException ex) {

            }
        }   
        webcam.close();
    }
}
