package Test;

import Main.Palindrome;
import org.junit.Test;

import static org.junit.Assert.*;

public class PalindromeTest {

    @Test
    public void palidromeR() {
        Palindrome palindrome =new Palindrome();
        assertTrue(palindrome.palidromeR("A man, a plan, a canal: Panama"));
        assertTrue(palindrome.palidromeR(" "));
        assertTrue(palindrome.palidromeR(""));
        assertFalse(palindrome.palidromeR("race a car"));
        assertFalse(palindrome.palidromeR("abc,bbbc"));

    }

    @Test
    public void palidromeP() {
        Palindrome palindrome =new Palindrome();
        assertTrue(palindrome.palidromeP("A man, a plan, a canal: Panama"));
        assertTrue(palindrome.palidromeP(" "));
        assertTrue(palindrome.palidromeP(""));
        assertFalse(palindrome.palidromeP("race a car"));
        assertFalse(palindrome.palidromeP("abc,bbbc"));
    }
}