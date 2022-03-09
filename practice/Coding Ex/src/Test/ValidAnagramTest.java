package Test;

import Main.ValidAnagram;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidAnagramTest {

    @Test
    public void isAnagramSort() {
        ValidAnagram V =new ValidAnagram();
        assertTrue(V.isAnagramSort("abbbc","bbbca"));
        assertTrue(V.isAnagramSort("abcdefgijk","abcdekjifg"));
        assertTrue(V.isAnagramSort("isanagram","anaismarg"));
        assertFalse(V.isAnagramSort("abbbc","bcbca"));
    }

    @Test
    public void isAnagramFaster() {
        ValidAnagram V =new ValidAnagram();
        assertTrue(V.isAnagramFaster("abbbc","bbbca"));
        assertTrue(V.isAnagramFaster("abc","cba"));
        assertTrue(V.isAnagramFaster("isanagram","anaismarg"));
        assertFalse(V.isAnagramFaster("abbbc","bcbca"));
    }
}