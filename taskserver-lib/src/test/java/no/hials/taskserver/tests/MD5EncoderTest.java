/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.taskserver.tests;

import no.hials.taskserver.impl.MD5Encoder;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author girts
 */
public class MD5EncoderTest {
    @Test
    public void testEncoding() {
        MD5Encoder encoder = new MD5Encoder();
        assertNull(encoder.encode(null));
        assertEquals("b7a30912f2b133744c1186d3e2fb6bce", encoder.encode("lapsa"));
        assertEquals("827ab9a87eb25a0990a6473b5081cdbb", encoder.encode("Apelsin456"));
    }
}
