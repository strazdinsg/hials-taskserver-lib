package no.hials.taskserver.impl;

/**
 * Error message saying: Feature not implemented
 * @author Girts Strazdins 
 * girts.strazdins@gmail.com
 * 2015-10-31 
 */
public class NotImplementedMsg extends ResultMsg {
    /**
     * Create an outgoing ResultMsg with specific code and text message
     */
    public NotImplementedMsg() {
        super(ResultCode.NOT_IMPLEMENTED, "Feature not implemented");
    }
    
    public NotImplementedMsg(String message) {
        super(ResultCode.NOT_IMPLEMENTED, message);
    }
}
