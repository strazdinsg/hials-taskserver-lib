package no.hials.taskclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.hials.taskserver.AppLogic;
import no.hials.taskserver.Message;
import no.hials.taskserver.impl.MessageImpl;

/**
 *
 * @author Girts Strazdins 
 * girts.strazdins@gmail.com
 * 2015-11-05 
 */
public class TestClient {
    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    
    public static String SERVER_ADDRESS = "128.199.49.119";
    public static int SERVER_PORT = 58732;
    
    public static void main(String[] args) {
        TestClient c = new TestClient();
        c.run();   
    }

    private void run() {
        System.out.println("Task client running");
        if (connect() == false) {
            System.out.println("Could not connect to the server");
            return;
        }
        
        boolean running = true;
        AppLogic logic = createAppLogic();
        if (logic != null) {
            logic.init();
        } else {
            running = false;
        }
        
        while (running) {
            
            // Sending pending messages TO the server
            Message outMsg;
            while ((outMsg = logic.getResponseMessage()) != null) {
                try {
                    out.writeBytes(outMsg.format());
                } catch (IOException ex) {
                    running = false;
                }
            }
            
            if (running && logic.shouldQuit() == false) {
                // Read one message from the server
                Message inMsg = new MessageImpl(false);
                while (inMsg.isReady() == false) {
                    byte b;
                    try {
                        b = in.readByte();
                        inMsg.addByte(b);
                    } catch (IOException ex) {
                        running = false;
                        break;
                    }
                }
                if (inMsg.isReady()) {
                    logic.messageReceived(inMsg);
                }
                
            } else {
                running = false;
            }
            
        }
        
        close();
    }

    private boolean connect() {
        try {
            socket = new Socket(InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException ex) {
            System.out.println("Unknown host");
            return false;
        } catch (IOException ex) {
            System.out.println("IO Exception");
            return false;
        }        
        
        return true;
    }

    private void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private AppLogic createAppLogic() {
        return new MyCoolLogic();
    }
    
}
