package no.hials.taskserver.impl;

import no.hials.taskserver.PasswordEncoder;

/**
 * Plaintext password encoder - does not actually encode anything
 * @author Girts Strazdins 
 * girts.strazdins@gmail.com
 * 2015-10-31 
 */
public class PlaintextEncoder implements PasswordEncoder {

    public static final String NAME = "plaintext";
    
    @Override
    public String getName() {
        return NAME;
    }

    /**
     * Return the original plaintext password
     * @param plaintextPassword
     * @return 
     */
    @Override
    public String encode(String plaintextPassword) {
        return plaintextPassword;
    }

}
