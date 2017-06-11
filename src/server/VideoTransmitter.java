package server;

import com.github.sarxos.webcam.Webcam;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import message.Message;
import message.Utils;
import message.VideoPacket;
import myclassroom.ItsMe;
import myclassroom.MainFrame;

/****** @author vicky ******/

public class VideoTransmitter implements Runnable{
    
    Webcam webcam;
    ItsMe itsMe;
    String name;
    int seatNumber;
    String id;
    MainFrame mainFrame;
    
    public VideoTransmitter(String name, int seatNumber, String id, ItsMe itsMe, MainFrame mainFrame) {
        this.name = name;
        this.seatNumber = seatNumber;
        this.itsMe = itsMe;
        this.id = id;
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
        
        cool.clear();
        
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
                int size;
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
                        temp.out.flush();
                    }
                }
            } catch (IOException ex) {

            }
        
            Utils.sleep(100);
        }  
        System.out.println("webcam is closed");
        webcam.close();
    }
}
