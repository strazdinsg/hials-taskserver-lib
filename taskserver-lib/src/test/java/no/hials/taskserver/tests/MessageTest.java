package no.hials.taskserver.tests;

import no.hials.taskserver.Message;
import no.hials.taskserver.impl.MessageImpl;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test package for Message parser class
 * @author Girts Strazdins 
 * girts.strazdins@gmail.com
 * 2015-10-29 
 */
public class MessageTest {

    @Test
    public void testMessageFormatting() {
        Message msg = new MessageImpl(true);
        assertEquals(false, msg.isReady());
        msg.setParamValue("username", "Girts");
        assertEquals(true, msg.isReady());
        assertEquals(">username=Girts$", msg.format());
        msg.setParamValue("password", "Apelsing456");
        String f = msg.format();
        // Parameters can by in any sequence 
        assertTrue(">username=Girts#password=Apelsin456$".equals(f) 
                || ">password=Apelsing456#username=Girts$".equals(f));
    }
  
    @Test
    public void buildTest() {
        String msgData = "  >username=Girts#password=Apelsin456$   ";
        char[] msgChars = msgData.toCharArray();
        Message msg = new MessageImpl(false);
        for (char c : msgChars) {
            msg.addByte((byte) c);            
        }
        assertEquals("Girts", msg.getParamValue("username"));
        assertNotSame("girts", msg.getParamValue("username"));
        assertEquals("Apelsin456", msg.getParamValue("password"));
        assertEquals("Apelsin456", msg.getParamValue("PASSWORD"));
        assertEquals(true, msg.isReady());

        msgData = "$$$  >username=Girts#password=Apelsin456$$$   ";
        msgChars = msgData.toCharArray();
        msg.clear();
        assertEquals(false, msg.isReady());
        for (char c : msgChars) {
            msg.addByte((byte) c);            
        }
        assertEquals("Girts", msg.getParamValue("username"));
        assertEquals("Apelsin456", msg.getParamValue("password"));
        assertEquals(true, msg.isReady());
    }
}
