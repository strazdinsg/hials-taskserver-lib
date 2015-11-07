package no.hials.taskserver.impl;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test package for RequestMsg
 * @author Girts Strazdins 
 * girts.strazdins@gmail.com
 * 2015-11-08
 */
public class TaskRequestMsgTest {
    
    public TaskRequestMsgTest() {
    }
    
    /**
     * Test of createFrom method, of class TaskRequestMsg.
     */
    @Test
    public void testCreateFrom() {
        MessageImpl srcMsg = new MessageImpl(false);
        srcMsg.setCommand("task");
        srcMsg.setParamValue("taskNr", "1");
        TaskRequestMsg msg = TaskRequestMsg.createFrom(srcMsg);
        assertNotNull(msg);
        assertEquals(TaskNumber.TEST, msg.getTaskNumber());
    }

    /**
     * Test of setTaskNumber method, of class TaskRequestMsg.
     */
    @Test
    public void testSetTaskNumber() {
        TaskRequestMsg msg = new TaskRequestMsg(TaskNumber.TEST, true);
        assertEquals(TaskNumber.TEST, msg.getTaskNumber());
        msg.setTaskNumber(TaskNumber.PIN_CRACK);
        assertEquals(TaskNumber.PIN_CRACK, msg.getTaskNumber());
    }
}
