package no.hials.taskserver.impl;

/**
 * Result codes used in the ResultMsg
 * @author Girts Strazdins 
 * girts.strazdins@gmail.com
 * 2015-10-31
 */
public enum ResultCode { 
    /** No error, all ok */
    RES_OK,
    LOGIN_ERROR,
    /** Some internal error of the server (not client's fault) */
    INTERNAL_ERROR,
    /** Not allowed to perform this action before authorization */
    UNAUTHORIZED,
    /** Other error. Check message to understand details */
    OTHER,
    /** Incorrect message structure received from the client */
    INCORRECT_MSG,
    /** The solution to this task is not correct */
    INCORRECT_SOLUTION,
    /** This should be used by the client to request next task from the server 
     * without solving the current one */
    NEXT_TASK,
    /** This should be used by the client to request stopping the tasks */
    DONE_WITH_TASKS,
    /** Too much time since last message */
    TIMEOUT,
    /** This feature is currently not implemented */
    NOT_IMPLEMENTED;
    
    /**
     * Return true if this result code value is allowed
     * @param code
     * @return 
     */
    public static boolean isValid(int code) {
        return code >= 0 && code < values().length;
    }
    
    /**
     * Converts integer to ResultCode enum
     * @param code
     * @return enum value or null if the integer value is out of bounds
     */
    public static ResultCode fromInt(int code) {
        if (isValid(code)) {
            return values()[code];
        } else {
            return null;
        }
    }
    
    /**
     * Converts string representation of a number to ResultCode enum
     * @param codeStr
     * @return enum value or null if the integer value is out of bounds
     */
    public static ResultCode fromString(String codeStr) {
        if (codeStr == null) return null;
        try {
            return fromInt(Integer.valueOf(codeStr));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}