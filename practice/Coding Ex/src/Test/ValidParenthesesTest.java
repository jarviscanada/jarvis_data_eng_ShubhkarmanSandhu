package Test;

import Main.ValidParentheses;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidParenthesesTest {

    @Test
    public void isValid() {
        ValidParentheses v =new ValidParentheses();
        assertTrue(v.isValid("{}[]"));
        assertTrue(v.isValid("{[12+3(6)]}"));
        assertTrue(v.isValid("{}[()]"));
        assertTrue(v.isValid("{abc}[]"));
        assertFalse(v.isValid("[)"));
        assertFalse(v.isValid("[]{"));
    }
}