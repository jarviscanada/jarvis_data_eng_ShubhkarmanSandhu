package Main;

import java.util.Arrays;
import java.util.OptionalInt;

public class LargestAndSmallest {
    public void oneLoop(int array[]){
        int smallest=array[0];
        int largest=array[0];
        for(int num:array){
            if(num<smallest) smallest=num;
            if(num>largest)  largest=num;
        }
        System.out.println("Largest:"+largest+"  Smallest:"+smallest);

    }
    public void stream(int array[]){
         int largest= Arrays.stream(array)
                      .max()
                      .getAsInt();
         int smallest=Arrays.stream(array)
                      .min()
                      .getAsInt();
         System.out.println("Largest:"+largest+"  Smallest:"+smallest);
    }
    public void max(int array[]){
        Arrays.sort(array);
        int largest=array[array.length-1];
        int smallest=array[0];
        System.out.println("Largest:"+largest+"  Smallest:"+smallest);

    }

    public static void main(String[] args) {
        LargestAndSmallest LS=new LargestAndSmallest();
        int array[]={23,67,190,567,32,22,90,345};
        LS.oneLoop(array);
        LS.max(array);
        LS.stream(array);
    }
}
