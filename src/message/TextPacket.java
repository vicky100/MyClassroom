package message;

import java.io.Serializable;

/****** @author vicky *******/

public class TextPacket implements Serializable {
 
    private final int dataLength;
    private final String data;
    
    public TextPacket(int dataLength, String data) {
        this.dataLength = dataLength;
        this.data = data;
    }
    
    public TextPacket(String data) {
        this(data.length(), data);
    }
    
    public String getData() {
        return data;
    }
}
