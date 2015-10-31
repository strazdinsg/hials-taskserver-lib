package no.hials.taskserver;

import java.io.DataOutputStream;

/**
 * Interface used to construct, parse and prepare/format messages exchanged
 * between the client and server
 * 
 * @author Girts Strazdins 
 * girts.strazdins@gmail.com
 * 2015-10-29 
 */
public interface Message {
    /**
     * Clears all previously stored parameters and resets the parsing state
     */
    public void clear();
    
    /**
     * Add one byte received from the socket to the message constructor.
     * After calling this function you should check the isReady() to see
     * if the message is received in whole and is ready to be handled by the app
     * @param b 
     */
    public void addByte(byte b);
    
    /**
     * For incoming messages returns true when the whole message is received 
     * correctly and ready to be handled. 
     * For outgoing messages returns true when the message is ready to be sent.
     * @return 
     */
    public boolean isReady();
    
    /**
     * Get a value of a parameter in the message by parameter name
     * @param paramName
     * @return parameter value or null if it is not found in the message
     *     Will also return null if the message is not ready yet
     */
    public String getParamValue(String paramName);
    
    /**
     * Sets parameter for an outgoing message
     * @param paramName
     * @param paramValue 
     */
    public void setParamValue(String paramName, String paramValue);
    
    /**
     * Takes the whole content in the message currently and formats it 
     * according to the protocol so that it can be sent over the socket
     * @return 
     */
    public String format();

    /**
     * Return contents of the message in a human-readable form
     * @return 
     */
    public String dump();
    
    /**
     * Send the message to an output stream
     * @param out
     * @return true when successfully sent, false otherwise
     */
    public boolean sendToStream(DataOutputStream out);
}
