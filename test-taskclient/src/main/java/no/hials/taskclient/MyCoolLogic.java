package no.hials.taskclient;

import no.hials.taskserver.Message;
import no.hials.taskserver.impl.AbstractAppLogic;
import no.hials.taskserver.impl.LoginRequestMsg;

/**
 *
 * @author Girts Strazdins 
 * girts.strazdins@gmail.com
 * 2015-11-05 
 */
class MyCoolLogic extends AbstractAppLogic {

    boolean shouldQuit;
    
    @Override
    public void init() {
        LoginRequestMsg msg = new LoginRequestMsg();
        msg.setUsername("Girts");
        msg.setPlaintextPassword("Apelsin456");
        System.out.println("Message to send: " + msg.format());
        
        enqueueResponse(msg);
        
        shouldQuit = false;
    }

    @Override
    public void messageReceived(Message inMsg) {
        System.out.println("Received message: " + inMsg.format());
        shouldQuit = true;
    }

    @Override
    public boolean shouldQuit() {
        return shouldQuit;
    }
}
