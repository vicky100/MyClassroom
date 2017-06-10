package message;

import java.io.Serializable;

/****** @author vicky ******/

public class Message implements Serializable{
    
    /****** name of the student/teacher ******/
    private String name;
    
    /****** this is the number of image panel alloted to this client by server ******/
    private int seatNumber;
    
    /****** -1 from server to client, 1 for client to server ******/
    private int type;
    
    
    /***********************************
    code #1 : simple text message
    code #2 : audio frames
    code #3 : video frames
    ***********************************/
    private int code; 
    
    
    /*** uniquely identify user from server side ***/
    private String id; 
    
    
    /*** actual data of message ***/
    private Object data;
    
    

    public Message(String name, int seatNumber, int type, int code, String id, Object data) {
        this.name = name;
        this.seatNumber = seatNumber;
        this.type = type;
        this.code = code;
        this.id = id;
        this.data = data;
    }
    
    //this is used by StudentHandler
    public Message(int seatNumber, int type, int code, String id, Object data) {
        this(id, seatNumber, type, code, id, data);
    }
    
    //this is used by Student
    public Message(String name, int type, int code, Object data) {
        this(name, -1, type, code, name, data);
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void assignSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getSeatNumber() {
        return this.seatNumber;
    }
    
    public int getCode() {
        return this.code;
    }
    
    public int getType() {
        return this.type;
    }
    
    public String getID() {
        return this.id;
    }
    
    public Object getData() {
        return this.data;
    }
    
}
