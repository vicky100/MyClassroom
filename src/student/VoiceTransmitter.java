package student;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import message.AudioPacket;
import message.Message;
import message.Utils;
import myclassroom.ItsMe;


public class VoiceTransmitter implements Runnable{

    String name;
    int seatNumber;
    String id;
    ItsMe itsMe;
    ObjectOutputStream out;
    
    TargetDataLine mic;
    
    static double amplification = 1.0;
    
    
    VoiceTransmitter(String name, int seatNumber, String id, ItsMe itsMe, ObjectOutputStream out) {
        this.name = name;
        this.seatNumber =seatNumber;
        this.id = id;
        this.itsMe = itsMe;
        this.out = out;
        
        AudioFormat af = getAudioFormat();
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, null);
        try {
            mic = (TargetDataLine) (AudioSystem.getLine(info));
            mic.open(af);
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
        mic.flush();
        mic.start();
        while(itsMe.voiceCall) {
            if (mic.available() >= AudioPacket.defaultDataLength) {
                byte[] buff = new byte[AudioPacket.defaultDataLength];
                while (mic.available() >= AudioPacket.defaultDataLength) {
                    mic.read(buff, 0, buff.length);
                }
                try {
                        
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    GZIPOutputStream go = new GZIPOutputStream(baos);
                    go.write(buff);
                    go.flush();
                    go.close();
                    baos.flush();
                    baos.close();
                    Message m = new Message(name, seatNumber, 1, 2, id, new AudioPacket(baos.toByteArray()));  //create message for server, will generate chId and timestamp from this computer's IP and this socket's port 
                    
                    out.writeObject(m);
                } catch (IOException ex) {
                    
                }
            } else {
                Utils.sleep(10);
            }
        }
        mic.stop();
    }
    
}
