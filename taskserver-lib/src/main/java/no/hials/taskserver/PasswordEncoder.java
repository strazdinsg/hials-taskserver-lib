package no.hials.taskserver;

/**
 * Interface for password encoding
 * 
 * @author Girts Strazdins 
 * girts.strazdins@gmail.com
 * 2015-10-31 
 */
public interface PasswordEncoder {
    /**
     * Each encoder should have a name
     * @return 
     */
    public String getName();
    
    /**
     * Take a plaintext password and output encoded version
     * @param plaintextPassword
     * @return encoded password
     */
    public String encode(String plaintextPassword);
}
