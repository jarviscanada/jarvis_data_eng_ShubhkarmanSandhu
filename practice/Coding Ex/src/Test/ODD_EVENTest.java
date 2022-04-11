package Test;

import Main.ODD_EVEN;

import static org.junit.Assert.*;

public class ODD_EVENTest {

    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.Test
    public void checkMod() {
        ODD_EVEN odd_even=new ODD_EVEN();
        assertTrue(odd_even.checkMod(0));
        assertTrue(odd_even.checkMod(8));
        assertTrue(odd_even.checkMod(1298));
        assertFalse(odd_even.checkMod(1));
        assertFalse(odd_even.checkMod(7));
        assertFalse(odd_even.checkMod(1297));
    }


    @org.junit.Test
    public void checkBit() {
        ODD_EVEN odd_even=new ODD_EVEN();
        assertTrue(odd_even.checkBit(0));
        assertTrue(odd_even.checkBit(8));
        assertTrue(odd_even.checkBit(1298));
        assertFalse(odd_even.checkBit(1));
        assertFalse(odd_even.checkBit(7));
        assertFalse(odd_even.checkBit(1297));
    }
}