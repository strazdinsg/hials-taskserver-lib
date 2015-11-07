/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.taskserver.impl;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test for task response
 * @author Girts Strazdins 
 * girts.strazdins@gmail.com
 * 2015-11-08 
 */
public class TaskResponseMsgTest {
    @Test
    public void testIt() {
        System.out.println("createFrom");
        MessageImpl srcMsg = new MessageImpl(false);
        srcMsg.setCommand("taskResponse");
        String result = "This is {result}";
        srcMsg.setParamValue("result", result);
        TaskResponseMsg msg = TaskResponseMsg.createFrom(srcMsg);
        assertNotNull(msg);
        assertEquals(msg.getResult(), result);
        assertEquals(msg.getCommand(), TaskResponseMsg.CMD);
    }
}
