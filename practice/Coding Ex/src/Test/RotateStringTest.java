package Test;

import Main.RotateString;
import org.junit.Test;

import static org.junit.Assert.*;

public class RotateStringTest {

    @Test
    public void rotateString() {
        RotateString RS=new RotateString();
        assertTrue(RS.rotateString("abcde","deabc"));
        assertFalse(RS.rotateString("abcde","edabc"));
        assertTrue(RS.rotateString("helloworld","worldhello"));

    }
}