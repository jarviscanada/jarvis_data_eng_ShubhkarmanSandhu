package Test;

import Main.OnlyDigits;
import org.junit.Test;

import static org.junit.Assert.*;

public class OnlyDigitsTest {

    @Test
    public void ascii() {
        OnlyDigits OD=new OnlyDigits();
        assertTrue(OD.ascii("1234567890"));
        assertFalse(OD.ascii("123456,7890"));
        assertFalse(OD.ascii("123456acb7890"));
    }

    @Test
    public void javaAPI() {
        OnlyDigits OD=new OnlyDigits();
        assertTrue(OD.javaAPI("1234567890"));
        assertFalse(OD.javaAPI("123456,7890"));
        assertFalse(OD.javaAPI("123456acb7890"));
    }

    @Test
    public void regex() {
        OnlyDigits OD=new OnlyDigits();
        assertTrue(OD.regex("1234567890"));
        assertFalse(OD.regex("123456,7890"));
        assertFalse(OD.regex("123456acb7890"));
    }
}