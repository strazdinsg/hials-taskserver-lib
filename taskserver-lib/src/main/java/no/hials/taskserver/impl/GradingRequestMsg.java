package no.hials.taskserver.impl;

/**
 * Grading Request message
 * @author Girts Strazdins 
 * girts.strazdins@gmail.com
 * 2015-11-01 
 */
public class GradingRequestMsg extends MessageImpl {
    // Predefined value for the command parameter
    public final static String CMD = "getGrade";

    public GradingRequestMsg(boolean forSending) {
        super(forSending);
        setCommand(CMD);        
    }
}
