package Main;

import java.util.HashSet;
import java.util.Set;

public class RepeatedNum {
    public int findDuplicate(int[] nums) {
        Set<Integer> set=new HashSet<Integer>();

        for(int num:nums){

            if(set.contains(num)) return num;

            else set.add(num);

        }
        throw new IllegalArgumentException("no match");
    }
}
