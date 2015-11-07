package no.hials.taskserver.impl;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author girts
 */
public class ResultCodeTest {
    
    /**
     * Test of valueOf method, of class ResultCode.
     */
    @Test
    public void testValueOf() {
        String name = "RES_OK";
        ResultCode expResult = ResultCode.RES_OK;
        ResultCode result = ResultCode.valueOf(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of isValid method, of class ResultCode.
     */
    @Test
    public void testIsValid() {
        assertFalse(ResultCode.isValid(-1));
        assertTrue(ResultCode.isValid(0));
        assertTrue(ResultCode.isValid(1));
        assertTrue(ResultCode.isValid(10));
        assertFalse(ResultCode.isValid(11));
    }

    /**
     * Test of fromInt method, of class ResultCode.
     */
    @Test
    public void testFromInt() {
        assertNull(ResultCode.fromInt(-1));
        assertNull(ResultCode.fromInt(11));
        assertEquals(ResultCode.RES_OK, ResultCode.fromInt(0));
        assertEquals(ResultCode.INCORRECT_MSG, ResultCode.fromInt(5));
        assertEquals(ResultCode.NOT_IMPLEMENTED, ResultCode.fromInt(10));
    }

    /**
     * Test of fromString method, of class ResultCode.
     */
    @Test
    public void testFromString() {
        assertNull(ResultCode.fromString("-1"));
        assertNull(ResultCode.fromString("11"));
        assertEquals(ResultCode.RES_OK, ResultCode.fromString("0"));
        assertEquals(ResultCode.INCORRECT_MSG, ResultCode.fromString("5"));
        assertEquals(ResultCode.NOT_IMPLEMENTED, ResultCode.fromString("10"));
    }
    
}
