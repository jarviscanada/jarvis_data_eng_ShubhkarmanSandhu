package Main;

import java.util.HashSet;
import java.util.Set;

public class containDup {
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set=new HashSet<>();
        for(int num:nums){
            if(set.contains(num))
                return true;
            else
                set.add(num);
        }
        return false;
    }
    public int removeDuplicatesSortArray(int[] nums) {
        int i=1;
        int j=0;

        for(;i<nums.length;i++)
        {
            if(nums[i]!=nums[j])
            {
                nums[++j]=nums[i];

            }
        }
        return j+1;

    }
}
