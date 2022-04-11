package Main;

import java.util.HashSet;
import java.util.Set;

public class FindMissing {


    public int FM_Guass(int nums[]){
        int sum=0;
        int l=nums.length;
        for(int num:nums) {
            sum=sum+num;
        }
        return (l*(l+1)/2)-sum;
    }

    public int FM_sum(int nums[]){
        int sum=0;
        int sumIndex=0;
        int l=nums.length;
        for(int i=0;i<l;i++) {
            sumIndex=sumIndex+i;
            sum=sum+nums[i];
        }
        sumIndex=sumIndex+l;

        return sumIndex-sum;
    }
    public int FM_set(int nums[]){

           return 0;
    }

    public static void main(String[] args) {


    }
}
