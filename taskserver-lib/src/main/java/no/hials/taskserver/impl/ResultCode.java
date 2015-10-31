package no.hials.taskserver.impl;

/**
 * Result codes used in the ResultMsg
 * @author Girts Strazdins 
 * girts.strazdins@gmail.com
 * 2015-10-31
 */
public enum ResultCode { 
    RES_OK,
    LOGIN_ERROR,
    INTERNAL_ERROR,
    UNAUTHORIZED,
    OTHER,
    INCORRECT_MSG,
    INCORRECT_SOLUTION,
    NEXT_TASK,
    DONE_WITH_TASKS,
    TIMEOUT,
    NOT_IMPLEMENTED;
    
    /**
     * Return true if this result code value is allowed
     * @param code
     * @return 
     */
    public static boolean isValid(int code) {
        return code >= 0 && code < ResultCode.values().length;
    }
    
    /**
     * Converts integer to ResultCode enum
     * @param code
     * @return enum value or null if the integer value is out of bounds
     */
    public static ResultCode fromInt(int code) {
        if (isValid(code)) {
            return ResultCode.values()[code];
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