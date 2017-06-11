package student;

import com.github.sarxos.webcam.Webcam;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import message.Message;
import message.VideoPacket;
import myclassroom.ItsMe;
import myclassroom.MainFrame;

/****** @author vicky ******/

public class VideoTransmitter implements Runnable{
    
    Webcam webcam;
    ItsMe itsMe;
    ObjectOutputStream out;
    String name;
    int seatNumber;
    String id;
    MainFrame mainFrame;
        
    public VideoTransmitter(String name, int seatNumber, String id, ItsMe itsMe, ObjectOutputStream out, MainFrame mainFrame) {
        this.name = name;
        this.seatNumber = seatNumber;
        this.id = id;
        this.itsMe = itsMe;
        this.out = out;
        this.mainFrame = mainFrame;
    }


    @Override
    public void run() {
        System.out.println("webcam is opening...");
        
        List<Webcam> webcams = Webcam.getWebcams();
        Map<String, Integer> cool = new HashMap<String, Integer>();
        String[] webcamNames = new String[webcams.size()];
        for(int i=0;i<webcams.size();i++) {
            webcamNames[i] = webcams.get(i).getName();
            cool.put(webcamNames[i], i);
        }
        
        String option = (String) JOptionPane.showInputDialog(mainFrame, "Choose your webcam", "Webcam", JOptionPane.QUESTION_MESSAGE, null, webcamNames, webcamNames[0]);
        webcam = webcams.get(cool.get(option));
        
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
