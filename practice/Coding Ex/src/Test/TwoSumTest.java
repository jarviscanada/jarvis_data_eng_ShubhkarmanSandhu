package Test;

import Main.TwoSum;
import org.junit.Test;

import static org.junit.Assert.*;

public class TwoSumTest {

    @Test
    public void twoSumBrute() {
        int array1[]={2,3,5,1,9,8};
        int target1 =14;
        int array2[]={67,52,3,5,1,9,8,45,23,83};
        int target2 =60;
        TwoSum twoSum=new TwoSum();
        assertArrayEquals(new int[]{2, 4},twoSum.twoSumBrute(array1,target1));
        assertArrayEquals(new int[]{1, 6},twoSum.twoSumBrute(array2,target2));
    }

    @Test
    public void twoSumFaster() {
        int array1[]={2,3,5,1,9,8};
        int target1 =14;
        int array2[]={67,52,3,5,1,9,8,45,23,83};
        int target2 =60;
        TwoSum twoSum=new TwoSum();
        assertArrayEquals(new int[]{2, 4},twoSum.twoSumFaster(array1,target1));
        assertArrayEquals(new int[]{1, 6},twoSum.twoSumFaster(array2,target2));

    }
}