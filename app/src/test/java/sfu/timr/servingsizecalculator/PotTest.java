package sfu.timr.servingsizecalculator;

import org.junit.Test;

import static org.junit.Assert.*;

public class PotTest {
    private Pot testPot = new Pot("test", 555);

    @Test
    public void getWeightInG() throws Exception {
        assertEquals(555, testPot.getWeightInG());
    }

    @Test
    public void setWeightInG() throws Exception {
        testPot.setWeightInG(215);
        assertEquals(215, testPot.getWeightInG());
    }

    @Test
    public void getName() throws Exception {
        assertEquals("test", testPot.getName());
    }

    @Test
    public void setName() throws Exception {
        testPot.setName("ANOTHER NAME WITH SPACES AND SYMBOLS!");
        assertEquals("ANOTHER NAME WITH SPACES AND SYMBOLS!", testPot.getName());
    }

    @Test (expected = IllegalArgumentException.class)
    public void setNameToEmptyString() throws Exception {
        testPot.setName("");
    }

    @Test (expected = IllegalArgumentException.class)
    public void setNameToNull() throws Exception {
        testPot.setName(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void setWeightToNegativeNumber() throws Exception {
        testPot.setWeightInG(-1);
    }
}