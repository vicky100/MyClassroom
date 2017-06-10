package message;

import java.io.Serializable;

/****** @author vicky *******/

public class VideoPacket implements Serializable {
    
    private final int dataLength;
    private final byte[] data;
    
    public VideoPacket(byte[] data, int dataLength) {
        this.data = data;
        this.dataLength = dataLength;
    }
    
    public byte[] getData() {
        return data;
    }
    
    public int getDataLength() {
        return this.dataLength;
    }
    
}
