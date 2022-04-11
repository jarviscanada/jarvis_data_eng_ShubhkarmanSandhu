package Test;

import Main.ATOI;
import org.junit.Test;

import static org.junit.Assert.*;

public class ATOITest {

    @Test
    public void ATOI_Parse() {
        ATOI atoi=new ATOI();
        assertEquals(atoi.ATOI_Parse("-145"),-145);
        assertEquals(atoi.ATOI_Parse("+-1"),0);
        assertEquals(atoi.ATOI_Parse("289abx"),289);
        assertEquals(atoi.ATOI_Parse("11111111111111111111"),Integer.MAX_VALUE);
        assertEquals(atoi.ATOI_Parse("-1111111111111111111"),Integer.MIN_VALUE);
        assertEquals(atoi.ATOI_Parse("abc 1928"),0);
        assertEquals(atoi.ATOI_Parse("  -123"),-123);
    }

    @Test
    public void ATOI_Custom() {
        ATOI atoi=new ATOI();
        assertEquals(atoi.ATOI_Custom("+-1"),0);
        assertEquals(atoi.ATOI_Custom("289abx"),289);
        assertEquals(atoi.ATOI_Custom("11111111111111111111"),Integer.MAX_VALUE);
        assertEquals(atoi.ATOI_Custom("-1111111111111111111"),Integer.MIN_VALUE);
        assertEquals(atoi.ATOI_Custom("abc 1928"),0);
        assertEquals(atoi.ATOI_Custom("  -123"),-123);
    }
}