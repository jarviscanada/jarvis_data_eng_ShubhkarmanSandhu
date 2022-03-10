package Main;

import java.lang.reflect.Array;

/**
 * a=9   1001
 * b=5   0101
 * a= a^b=  1001  a
 *          0101  b
 *       => 0011  a^b
 *
 * b= a^b=  0011  a
 *          0101  b
 *       => 1001  a^b  or original a
 *
 * a= a^b=  0011  a
 *          1001  b
 *       => 0101  a^b  or original b
 */
public class Swap {
    public static int[] bit(int array[]){
        array[0]=array[0]^array[1];
        array[1]=array[0]^array[1];
        array[0]=array[0]^array[1];

        return array;
    }
    public static int[] arth(int array[]){
        array[0]=array[0]-array[1];
        array[1]=array[1]+array[0];
        array[0]=array[1]-array[0];
        return array;

    }

    public static void main(String[] args) {
        int[] a=bit(new int[]{1, 2});
        System.out.println("["+a[0]+","+a[1]+"]");
    }
}
