package Test;

import Main.Fibonacci;
import org.junit.Test;

import static org.junit.Assert.*;

public class FibonacciTest {

    @Test
    public void fibRec() throws Exception {
        Fibonacci fibonacci=new Fibonacci();
        assertTrue(fibonacci.fibRec(4)==3);
        assertTrue(fibonacci.fibRec(5)==5);
        assertTrue(fibonacci.fibRec(6)==8);
        assertTrue(fibonacci.fibRec(7)==13);
    }

    @Test
    public void fibDyncProg() throws Exception {
        Fibonacci fibonacci=new Fibonacci();
        assertTrue(fibonacci.fibDyncProg(4)==3);
        assertTrue(fibonacci.fibDyncProg(7)==13);
        assertTrue(fibonacci.fibDyncProg(10)==55);
        assertTrue(fibonacci.fibDyncProg(25)==75025);

    }
}