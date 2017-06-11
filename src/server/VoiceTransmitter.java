package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
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

/****** @author vicky ******/

public class VoiceTransmitter implements Runnable{
    
    ItsMe itsMe;
    String name;
    int seatNumber;
    String id;
    
    TargetDataLine mic;
    
    
    public VoiceTransmitter(String name, int seatNumber, String id, ItsMe itsMe) {
        this.name = name;
        this.seatNumber = seatNumber;
        this.itsMe = itsMe;
        this.id = id;
        
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
                    Message res = new Message(name, seatNumber, 1, 2, id, new AudioPacket(baos.toByteArray()));
                    
                    Iterator<String> it = Server.studentMap.keySet().iterator();
            
                    while(it.hasNext()) {
                        String key = it.next();
                        StudentHandler temp = Server.studentMap.get(key);
                        if(temp != null) {
                            temp.out.writeObject(res);
                        }
                        temp.out.flush();
                    }
                } catch (IOException ex) {
                    
                }
            } else {
                Utils.sleep(10);
            }
        }
        mic.close();
    }
    
}
