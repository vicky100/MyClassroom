package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import myclassroom.MainFrame;

/****** @author vicky ******/

public class Server implements Runnable{
    
    private final int port;
    private final ServerSocket serverSocket;
    private final int maxClient;
    private Socket studentSocket;
    private String name;
    String id;
    MainFrame mainFrame;
    
    static Map<String, StudentHandler> studentMap;
    
    public Server(int port, String name, MainFrame mainFrame) throws IOException {
        this.port = port;
        this.name = name;
        this.mainFrame = mainFrame;
        this.studentMap = new ConcurrentHashMap<String, StudentHandler>();
        this.maxClient = 12;    
        serverSocket = new ServerSocket(this.port);
        
        SocketAddress inetAddress = serverSocket.getLocalSocketAddress();
        this.id = inetAddress+":"+port;
    }

    @Override
    public void run() {
        
        int seatNumber = 0;
        Teacher teacher = new Teacher(name, seatNumber, id, mainFrame);
        new Thread(teacher).start();
        
        while(true) {
            try{
                seatNumber++;
                studentSocket = serverSocket.accept();
                if(seatNumber < 12) {
                    StudentHandler studentHandler = new StudentHandler(studentSocket, studentMap, seatNumber, mainFrame);
                    studentMap.put(studentHandler.studentID, studentHandler);
                    new Thread(studentHandler).start();
                }
                else
                    studentSocket.close();
            }
            catch(IOException ex) {
                
            }
        }
    }
    
}
