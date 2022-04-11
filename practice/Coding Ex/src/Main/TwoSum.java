package Main;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    /**
     * Time complexity O(n^2)
     * @param nums
     * @param target
     * @return indices of two element which add up to target
     */
    public int[] twoSumBrute(int[] nums, int target){
        for(int i=0;i<nums.length;i++){
             int req=target-nums[i];
             for(int j=i+1;j<nums.length;j++) {
                 if(nums[j]==req){
                     return new int[]{i,j};
                 }
             }
        }
        throw  new IllegalArgumentException("no match");

    }
    /**
     * Time complexity O(n)
     * @param nums
     * @param target
     * @return indices of two element which add up to target
     */
    public int[] twoSumFaster(int[] nums, int target) {
        Map<Integer,Integer> map=new HashMap<>();
        for(int i=0;i<nums.length;i++)
        {
            int req=target-nums[i];
            if(map.containsKey(req))
            {
                return new int[]{map.get(req),i};
            }

            map.put(nums[i],i);

        }
        throw new IllegalArgumentException("no match");
    }
}
