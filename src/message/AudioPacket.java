package message;

import java.io.Serializable;

/****** @author vicky *******/

public class AudioPacket implements Serializable{
    
    public static int defaultDataLength = 1024;
    public  byte[] data;
    
    public AudioPacket() {}
    
    public AudioPacket(byte[] data) {
        this.data = data;
    }
    
    public byte[] getData() {
        return data;
    }
}
