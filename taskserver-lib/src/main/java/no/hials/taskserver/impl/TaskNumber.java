package no.hials.taskserver.impl;

/**
 * Task numbers
 * @author Girts Strazdins 
 * girts.strazdins@gmail.com
 * 2015-11-07 
 */
public enum TaskNumber {
    TEST, ECHO, ARITHMETIC, PIN_CRACK;

    /**
     * Return true if this result code value is allowed
     * @param code
     * @return 
     */
    public static boolean isValid(int code) {
        return code >= 0 && code < values().length;
    }
    
    /**
     * Converts integer to TaskNumber enum
     * @param number
     * @return enum value or null if the integer value is out of bounds
     */
    public static TaskNumber fromInt(int number) {
        if (isValid(number)) {
            return values()[number];
        } else {
            return null;
        }
    }
}
