package server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import message.AudioPacket;
import message.Message;
import message.Utils;
import myclassroom.MainFrame;

/****** @author vicky ******/

public class VoiceReceiver implements Runnable{
    
    
    static ArrayList<Message> queue = new ArrayList<Message>();
    MainFrame mainFrame;
    SourceDataLine speaker = null;
    
    public VoiceReceiver(MainFrame mainFrame) {
         this.mainFrame = mainFrame;
        try { 
            AudioFormat af = getAudioFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);
            speaker = (SourceDataLine) AudioSystem.getLine(info);
            speaker.open(af);
            speaker.start();
        } catch (LineUnavailableException ex) {
            
        }
    }
    
    private static AudioFormat getAudioFormat(){
        float sampleRate = 8000.0F; //8000, 11025, 16000, 22050, 44100
        int sampleSizeInBits = 16; //8, 16(littleEndian/bigEndian matters) 
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }
    
     @Override
    public void run() {
        while(true) {
            
            if (queue.isEmpty()) {
                Utils.sleep(20);
            } else {
                Message in = queue.get(0);
                queue.remove(in);
                if (in.getData() instanceof AudioPacket) {
                    GZIPInputStream gis = null;
                     
                    if(in.getData() instanceof AudioPacket) {
                        AudioPacket ap = (AudioPacket) (in.getData());
                        try {
                            gis = new GZIPInputStream(new ByteArrayInputStream(ap.getData()));
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            while (true) {
                                int b = gis.read();
                                if (b == -1) {
                                    break;
                                } else {
                                    baos.write((byte) b);
                                }
                            }
                            byte[] toPlay=baos.toByteArray();
                            speaker.write(toPlay, 0, toPlay.length);
                        } 
                        catch (IOException ex) {} 
                        finally {
                            try {
                                if(gis != null)
                                    gis.close();
                            } catch (IOException ex) {}
                        }
                    }    
                }
            }
        }    
    }
}
