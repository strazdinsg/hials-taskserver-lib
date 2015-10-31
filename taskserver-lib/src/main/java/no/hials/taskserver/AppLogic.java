package no.hials.taskserver;

/**
 * An interface for Application logic at the client or server side
 * @author Girts Strazdins 
 * girts.strazdins@gmail.com
 * 2015-10-31 
 */
public interface AppLogic {

    /**
     * Initialize the logic
     */
    public void init();
    
    /**
     * Handle a message received from the other side
     * @param inMsg 
     */
    void messageReceived(Message inMsg);

    /**
     * The app logic will add outgoing messages to a queue
     * This function pops one message from the output queue
     * @return next queued message or null if the queue is empty
     */
    Message getResponseMessage();

}
