package no.hials.taskserver.impl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import no.hials.taskserver.Message;

/**
 * Possible states that the message parser can be in
 * Wait start - empty message, waiting for the start symbol
 * Parse param name - start symbol received, reading parameter name currently
 * Parse param value - param name received, parsing value currently
 * Ready - done parsing, all fine
 * Invalid - done parsing, something was wrong with the params
 * @author gist
 */
enum State { WAIT_START, PARSE_PARAM_NAME, PARSE_PARAM_VALUE, READY, INVALID }

/**
 * Implementation of message encoding protocol, described in Lab 06
 * @author Girts Strazdins 
 * girts.strazdins@gmail.com
 * 2015-10-29 
 */
public class MessageImpl implements Message {
    protected Map<String, String> parameters = new HashMap<>();
    
    /**
     * Key for command parameter
     */
    private static final String KEY_CMD = "cmd";
    
    
    private State state = State.WAIT_START;
    
    // When true, this message is meant for sending
    private boolean forSending;
   
    // Separators
    // General message format: >key1=val1#key2=val2#...$
    private static final char START_SYMBOL = '>'; 
    private static final char END_SYMBOL = '$'; 
    private static final char PARAM_SEPARATOR = '#'; 
    private static final char KEYVAL_SEPARATOR = '='; 
    
    /**
     * Temporary variable, used while parsing parameter name
     */
    private String tmpName = "";
    /**
     * Temporary variable, used while parsing parameter value
     */
    private String tmpValue = "";
    
    /**
     * Clears all previously stored parameters and resets parsing state
     */
    @Override
    public void clear() {
        parameters.clear();
        state = State.WAIT_START;
    }

    /**
     * Create a new message
     * @param forSending set it to true, if this message is message is meant
     * to be sent (not for receiving incoming message)
     * Warning: You should not reuse the same message for reading and writing!
     */
    public MessageImpl(boolean forSending) {
        this.state = State.WAIT_START;
        this.forSending = forSending;
    }
    
    @Override
    public boolean isForSending() {
        return forSending;
    }
    
    /**
     * Clone all data from source message to this one
     * Warning! Copies reference to the same parameter map!
     * @param srcMsg 
     */
    public void cloneFrom(MessageImpl srcMsg) {
        if (srcMsg != null) {
            this.parameters = srcMsg.parameters;
            this.state = srcMsg.state;
            this.forSending = srcMsg.forSending;
            this.tmpName = srcMsg.tmpName;
            this.tmpValue = srcMsg.tmpValue;
        }
    }
    
    @Override
    public boolean addByte(byte b) {
        char c = (char) b;
        
        // Always when start symbol received, forget the progress so far
        // Know that we wait for param name now
        if (START_SYMBOL == c) {
            clear();
            state = State.PARSE_PARAM_NAME;
            return true;
        }
        
        switch (state) {
            case WAIT_START:
            case READY:
            case INVALID:
                // we dismiss anything except start symbol which is handled above
                return false;

            case PARSE_PARAM_NAME:
                switch (c) {
                    case KEYVAL_SEPARATOR:
                        // Key-val separator received, start reading value
                        // Currently assume that the value is empty
                        tmpValue = "";
                        state = State.PARSE_PARAM_VALUE;
                        break;
                    case END_SYMBOL:
                    case PARAM_SEPARATOR:
                        // Something wrong in the structure, no = found
                        state = State.INVALID;
                        return false;
                    default:
                        // Must be regular character, so we append it to the param name
                        tmpName += c;
                        break;
                }
                break;

            case PARSE_PARAM_VALUE:
                switch (c) {
                    case PARAM_SEPARATOR:
                        // End of this param, start of a new one
                        flushParamTemp();
                        state = State.PARSE_PARAM_NAME;
                        break;
                    case KEYVAL_SEPARATOR:
                        // Another = char, that's an error
                        state = State.INVALID;
                        return false;
                    case END_SYMBOL:
                        // Done, end of the last value
                        flushParamTemp();
                        state = State.READY;
                        break;
                    default:
                        // Just another char in the value
                        tmpValue += c;
                        break;
                }
                break;
        }
        
        return true;
    }

    /**
     * Take the parameters that we have parsed so far, add them to param map 
     * if necessary, and reset the temp values
     */
    private void flushParamTemp() {
        if (!tmpName.equals("")) {
            // If the name is not empty, we store the param
            setParamValue(tmpName, tmpValue);
        }
        tmpName = tmpValue = "";
    }
    
    
    /**
     * Returns true if parsing parameters is done OR if this is an outgoing message
     * and there is at least one parameter stored
     * @return 
     */
    @Override
    public boolean isReady() {
        return (state == State.READY || isForSending())
                && !parameters.isEmpty();
    }

    /**
     * Get the value for particular parameter in the message
     * @param paramName case-insensitive
     * @return 
     */
    @Override
    public String getParamValue(String paramName) {
        if (paramName == null) return null;
        return parameters.get(paramName.toLowerCase());
    }
    
    /**
     * Get integer value of a parameter
     * @param paramName
     * @return int value or -1 if the value is not present or has incorrect format
     */
    public int getIntParam(String paramName) {
        String v = getParamValue(paramName);
        if (v == null) return -1;
        try {
            return Integer.valueOf(v);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Get boolean value of a parameter
     * @param paramName
     * @return true for "true", false otherwise
     */
    public boolean getBoolParam(String paramName) {
        String v = getParamValue(paramName);
        if (v == null) return false;
        return v.equalsIgnoreCase("true");
    }
    
    /**
     * Add a new parameter to the message
     * @param paramName always converted to lowercase
     * @param paramValue 
     */
    @Override
    public void setParamValue(String paramName, String paramValue) {
        if (paramName != null && !paramName.equals("")) {
            parameters.put(paramName.toLowerCase(), paramValue);
        }
    }
    
    /**
     * Set integer parameter value
     * @param paramName
     * @param value 
     */
    public void setIntParam(String paramName, int value) {
        setParamValue(paramName, String.valueOf(value));
    }
    
    /**
     * Set boolean parameter value
     * @param paramName
     * @param value 
     */
    public void setBoolParam(String paramName, boolean value) {
        setParamValue(paramName, value ? "true" : "false");
    }

    @Override
    public String format() {
        if (!isReady()) return null;
            
        String formatted = String.valueOf(START_SYMBOL);
        
        // Add parameters. Separator for all, except the first param
        boolean firstParam = true;
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            if (!firstParam) {
                formatted += PARAM_SEPARATOR;
            }
            formatted += entry.getKey() + KEYVAL_SEPARATOR + entry.getValue();
            firstParam = false;
        }
        
        formatted += END_SYMBOL;
        
        return formatted;
    }

    /**
     * Set the command parameter
     * @param cmd value to use
     */
    public void setCommand(String cmd) {
        setParamValue(KEY_CMD, cmd);        
    }
    
    /**
     * Get the command value in this message
     * @return 
     */
    public String getCommand() {
        return getParamValue(KEY_CMD);
    }

    @Override
    public boolean sendToStream(DataOutputStream out) {
        String data = format();
        if (data != null) {
            try {
                // Write bytes one by one
                for (int i = 0; i < data.length(); ++i) {
                    out.write((byte) data.charAt(i));
                }
                // Make sure all the buffered bytes are sent now
                out.flush();
                return true;
                
            } catch (IOException ex) {
                // Something wrong while sending to the stream
                return false;
            }
        } else {
            // Message not ready to be sent
            return false;
        }
    }
    
    /**
     * Return message contents in a readable format
     * @return 
     */
    @Override
    public String dump() {
        if (!isReady()) return "Message NOT READY!";
        
        String result = "";
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            result += "  P: " + entry.getKey() + ": " + entry.getValue() + "\r\n";
        }
        return result;
    }
}
