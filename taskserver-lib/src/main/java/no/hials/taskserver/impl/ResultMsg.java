package no.hials.taskserver.impl;

/**
 * Result/error message
 * @author Girts Strazdins 
 * girts.strazdins@gmail.com
 * 2015-10-31 
 */
public class ResultMsg extends MessageImpl {
    // Predefined keys
    public final static String KEY_CODE = "code";
    public final static String KEY_MESSAGE = "message";
    // Predefined value for ResultMsg command parameter
    public final static String CMD = "result";
    
    /**
     * Create an outgoing ResultMsg with specific code and text message
     * @param code
     * @param message
     */
    public ResultMsg(ResultCode code, String message) {
        super(true);
        setCommand(CMD);
        setCode(code);
        setMessage(message);
    }

    /**
     * Create a ResultMsg message from message content.
     * Can be used to cast from plain MessageImpl to specific ResultMsg
     * @param srcMsg source message to be copied
     * @return 
     */
    public static ResultMsg createFrom(MessageImpl srcMsg) {
        if (srcMsg == null) return null;
        ResultCode code = ResultCode.fromString(srcMsg.getParamValue(KEY_CODE));
        ResultMsg msg = new ResultMsg(code, srcMsg.getParamValue(KEY_MESSAGE));
        return msg;
    }
    
    /**
     * Get result/error code for the message
     * @return 
     */
    public ResultCode getCode() {
        String codeStr = getParamValue(KEY_CODE);
        return ResultCode.fromString(codeStr);
    }
    
    /**
     * Get (optional) text message added to the result 
     * @return 
     */
    public String getMessage() {
        return getParamValue(KEY_MESSAGE);
    }
    
    
    /**
     * Set result/error code for the message
     * @param code 
     */
    public void setCode(ResultCode code) {
        setParamValue(KEY_CODE, String.valueOf(code.ordinal()));
    }

    /**
     * Set optional text message for the result/error message
     * @param message 
     */
    public void setMessage(String message) {
        if (message != null) {
            setParamValue(KEY_MESSAGE, message);
        }
    }
}
