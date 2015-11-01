package no.hials.taskserver.impl;

/**
 * Task response message
 * @author Girts Strazdins 
 * girts.strazdins@gmail.com
 * 2015-11-01 
 */
public class TaskResponseMsg extends MessageImpl {
    // Predefined keys
    public final static String KEY_RESULT = "result";

    // Predefined value for the command parameter
    public final static String CMD = "taskResponse";

    public TaskResponseMsg(boolean forSending) {
        super(forSending);
        setCommand(CMD);        
    }

    /**
     * Create a TaskResponseMsg message from message content.
     * Can be used to cast from plain MessageImpl to specific TaskResponseMsg
     * @param srcMsg source message to be copied
     * @return 
     */
    static TaskResponseMsg createFrom(MessageImpl srcMsg) {
        TaskResponseMsg msg = new TaskResponseMsg(false);
        msg.cloneFrom(srcMsg);
        return msg;
    }
    
    /**
     * Set the result (response) for the task. As the response format depends
     * on each task, we accept string format here
     * @param result 
     */
    public void setResult(String result) {
        setParamValue(KEY_RESULT, result);
    }
    
    public String getResult() {
        return getParamValue(KEY_RESULT);
    }
}
