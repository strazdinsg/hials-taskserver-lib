package no.hials.taskserver.impl;

import java.util.HashMap;
import java.util.Map;
import no.hials.taskserver.PasswordEncoder;

/**
 * LoginRequest message
 * @author Girts Strazdins 
 * girts.strazdins@gmail.com
 * 2015-10-31 
 */
public class LoginRequestMsg extends MessageImpl {
    // Predefined keys
    private final static String KEY_USERNAME = "username";
    private final static String KEY_PASSWORD = "password";
    private final static String KEY_MODE = "mode";
    // Predefined value for LoginRequest command parameter
    private final static String CMD_LOGIN_REQUEST = "login";

    // Password encryption modes
    private final static String MODE_PLAINTEXT = "plaintext";
    private final static String MODE_MD5 = "md5";
    
    // All possible encoders stored here
    private final Map<String, PasswordEncoder> encoders = new HashMap<>();
    
    /**
     * Create an outgoing ResultMsg with specific code and text message
     * By default just plaintext encoder supported.
     * You can add new encoder by addEncoder()
     */
    public LoginRequestMsg() {
        super(true);
        setCommand(CMD_LOGIN_REQUEST);
        addEncoder(new PlaintextEncoder());
    }

    /**
     * Get username
     * @return 
     */
    public String getUsername() {
        return getParamValue(KEY_USERNAME);
    }
    
    /**
     * Get password. No way to find out the original password, if it is
     * encrypted. Have to check getMode() to see whether it is encrypted
     * @return 
     */
    public String getPassword() {
        return getParamValue(KEY_PASSWORD);
    }

    /**
     * Return password encryption mode
     * @return 
     */
    public String getMode() {
        return getParamValue(KEY_MODE);
    }
    
    /**
     * Set username
     * @param username
     */
    public void setUsername(String username) {
        if (username != null) {
            setParamValue(KEY_USERNAME, username);
        }
    }

    /**
     * Sets a plaintext password
     * @param password 
     */
    public void setPlaintextPassword(String password) {
        setPassword(password, PlaintextEncoder.NAME);
    }
    
    /**
     * Sets password. Currently supports only plaintext. If more modes
     * necessary, extend this class
     * @param password
     * @param mode 
     */
    public void setPassword(String password, String mode) {
        mode = mode.toLowerCase();
        PasswordEncoder encoder = encoders.get(mode);
        if (encoder != null) {
            // Mode supported
            setParamValue(KEY_MODE, mode);
            setParamValue(KEY_PASSWORD, encoder.encode(password));
        } else {
            throw new UnsupportedOperationException("Encoder " + mode 
                + " not supported. Suported encoders: "
                + encoders.keySet().toString());
        }
    }

    /**
     * Add a new password encoder for this message
     * @param encoder 
     */
    public void addEncoder(PasswordEncoder encoder) {
        if (encoder != null) {
            String name = encoder.getName();
            if (name != null) {
                encoders.put(name, encoder);
            }
        }
    }
}
