package no.hials.taskserver.tests;

import no.hials.taskserver.Message;
import no.hials.taskserver.PasswordEncoder;
import no.hials.taskserver.impl.LoginRequestMsg;
import no.hials.taskserver.impl.MessageImpl;
import no.hials.taskserver.impl.NotImplementedMsg;
import no.hials.taskserver.impl.PlaintextEncoder;
import no.hials.taskserver.impl.ResultCode;
import no.hials.taskserver.impl.ResultMsg;
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
        msg.setParamValue("password", "Apelsin456");
        String f = msg.format();
        // Parameters can by in any sequence 
        assertTrue(">username=Girts#password=Apelsin456$".equals(f) 
                || ">password=Apelsin456#username=Girts$".equals(f));
    }
  
    @Test
    public void buildTest() {
        String msgData = ">username=Girts#password=Apelsin456$";
        char[] msgChars = msgData.toCharArray();
        Message msg = new MessageImpl(false);
        // First add some garbage
        for (int i = 0; i < 3; ++i) {
            assertFalse(msg.addByte((byte) ' '));
        }
        // Then add meaningful content
        for (char c : msgChars) {
            assertTrue(msg.addByte((byte) c));            
        }
        // Then garbage again
        for (int i = 0; i < 3; ++i) {
            assertFalse(msg.addByte((byte) ' '));
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
    
    @Test
    public void commandTest() {
        MessageImpl msg = new MessageImpl(true);
        String cmd = msg.getCommand();
        assertNull(cmd);
        
        msg.setCommand("BlaBla");
        cmd = msg.getCommand();
        assertEquals("BlaBla", cmd);
    }
    
    @Test
    public void resultMsgTest() {
        ResultMsg msg = new ResultMsg(ResultCode.RES_OK, null);
        assertEquals(ResultCode.RES_OK, msg.getCode());
        assertNull(msg.getMessage());
        assertEquals("result", msg.getCommand());
        
        msg.setCode(ResultCode.NOT_IMPLEMENTED);
        assertEquals(ResultCode.NOT_IMPLEMENTED, msg.getCode());
        assertNull(msg.getMessage());
        
        String m = "This is an optional message";
        msg.setMessage(m);
        assertEquals(ResultCode.NOT_IMPLEMENTED, msg.getCode());
        assertEquals(m, msg.getMessage());
    }
    
    @Test
    public void notImplMsgTest() {
        NotImplementedMsg msg = new NotImplementedMsg();
        assertEquals(ResultCode.NOT_IMPLEMENTED, msg.getCode());
        assertEquals("result", msg.getCommand());
        
        String message = "This feature is not currently implemented. Stay tuned...";
        msg = new NotImplementedMsg(message);
        assertEquals(ResultCode.NOT_IMPLEMENTED, msg.getCode());
        assertEquals(message, msg.getMessage());
    }
    
    @Test
    public void loginMsgTest() {
        LoginRequestMsg msg = new LoginRequestMsg();
        assertEquals("login", msg.getCommand());
        msg.setUsername("Girts");
        assertEquals("Girts", msg.getUsername());
        assertNull(msg.getPassword());
        
        msg.setPlaintextPassword("Apelsin456");
        assertEquals("Apelsin456", msg.getPassword());
        assertEquals(PlaintextEncoder.NAME, msg.getMode());
    }
    
    @Test
    public void pwdEncoderTest() {
        LoginRequestMsg msg = new LoginRequestMsg();
        // Test encoders - a simple uppercase encoder
        msg.addEncoder(new PasswordEncoder() {
            @Override
            public String getName() {
                return "upcase";
            }

            /**
             * Just a test encoder which makes the password uppercase
             * @param plaintextPassword
             * @return 
             */
            @Override
            public String encode(String plaintextPassword) {
                return plaintextPassword != null ? plaintextPassword.toUpperCase() : null;
            }
        });
        
        msg.setPassword("Apelsin456", "upcase");
        assertEquals("APELSIN456", msg.getPassword());
        assertEquals("upcase", msg.getMode());
        assertNull(msg.getUsername());
    }
}
