package no.hials.taskserver.impl;

/**
 * Task request message
 * The content depends on each task. 
 * The only mandatory part is the command and task number
 * 
 * @author Girts Strazdins 
 * girts.strazdins@gmail.com
 * 2015-11-017
 */
public class TaskRequestMsg extends MessageImpl {
    // Predefined keys
    public final static String KEY_TASK_NR = "taskNr";

    // Predefined value for the command parameter
    public final static String CMD = "task";

    public TaskRequestMsg(boolean forSending) {
        super(forSending);
        setCommand(CMD);        
    }
    
    /**
     * Create task request with specific task number, should be used only
     * for outgoing messages (on the server side)
     * @param taskNr
     * @param forSending 
     */
    public TaskRequestMsg(TaskNumber taskNr, boolean forSending) {
        this(forSending);
        setTaskNumber(taskNr);
    }

    /**
     * Create a TaskRequestMsg message from message content.
     * Can be used to cast from plain MessageImpl to specific TaskRequestMsg
     * @param srcMsg source message to be copied
     * @return 
     */
    public static TaskRequestMsg createFrom(MessageImpl srcMsg) {
        TaskRequestMsg msg = new TaskRequestMsg(false);
        msg.cloneFrom(srcMsg);
        return msg;
    }
    
    /**
     * Set the result (response) for the task. As the response format depends
     * on each task, we accept string format here
     * @param taskNumber 
     */
    public void setTaskNumber(TaskNumber taskNumber) {
        if (taskNumber != null) {
            setIntParam(KEY_TASK_NR, taskNumber.ordinal() + 1);
        }
    }
    
    /**
     * Get task number
     * @return 
     */
    public TaskNumber getTaskNumber() {
        return TaskNumber.fromInt(getIntParam(KEY_TASK_NR) - 1);
        
    }
}
