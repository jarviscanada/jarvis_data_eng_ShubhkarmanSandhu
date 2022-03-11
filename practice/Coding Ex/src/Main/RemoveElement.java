package Main;

import java.util.Arrays;

public class RemoveElement {
    public static int sol(int[] nums, int val) {

        int j=0;
        for(int i=0;i<nums.length;i++)
        {
            if(nums[i]!=val)
            {
                nums[j]=nums[i];
                j++;
            }
        }
        return j;
    }

    public static void main(String[] args) {
        int a[]={2,4,5};
        System.out.println(sol(a,2));
        System.out.println("array:-");
        Arrays.stream(a).forEach(x-> System.out.print(x+" "));
    }
}
