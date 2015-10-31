package no.hials.taskserver.impl;

import java.util.LinkedList;
import java.util.Queue;
import no.hials.taskserver.AppLogic;
import no.hials.taskserver.Message;

/**
 * Abstract app-logic class implementing outgoing message queue
 * @author Girts Strazdins 
 * girts.strazdins@gmail.com
 * 2015-10-31 
 */
public abstract class AbstractAppLogic implements AppLogic {

    /**
     * The outgoing messages will be queued here
     */
    private final Queue<Message> responseMessages = new LinkedList<>();
    
    /**
     * Gets the first outgoing message waiting in the queue
     * @return the queued message or null if the queue is empty
     */
    @Override
    public Message getResponseMessage() {
        return responseMessages.poll();
    }
    
    /**
     * Add one message to the output queue
     * @param msg 
     */
    protected void enqueueResponse(Message msg) {
        if (msg != null) {
            responseMessages.add(msg);
        }
    }

}
