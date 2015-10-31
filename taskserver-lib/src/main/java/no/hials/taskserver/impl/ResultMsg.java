package no.hials.taskserver.impl;

/**
 * Result/error message
 * @author Girts Strazdins 
 * girts.strazdins@gmail.com
 * 2015-10-31 
 */
public class ResultMsg extends MessageImpl {
    // Predefined keys
    private final static String KEY_CODE = "code";
    private final static String KEY_MESSAGE = "message";
    // Predefined value for ResultMsg command parameter
    private final static String CMD_RESULTMSG = "result";
    
    /**
     * Create an outgoing ResultMsg with specific code and text message
     * @param code
     * @param message
     */
    public ResultMsg(ResultCode code, String message) {
        super(true);
        setCommand(CMD_RESULTMSG);
        setCode(code);
        setMessage(message);
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
