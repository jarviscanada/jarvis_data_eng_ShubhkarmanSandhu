package Main;

import java.util.regex.Pattern;

public class OnlyDigits {
    public boolean ascii(String str){
        char C[]=str.toCharArray();
        for(char c:C){
            int diff=c-0;
            if(c<48||c>57) return false;
        }
        return true;
    }
    public boolean javaAPI(String str){
        try{
            Integer integer=Integer.valueOf(str);
        }
        catch(NumberFormatException e){
            return false;
        }
          return true;
    }
    public boolean regex(String str){
         return Pattern.matches("\\d+",str);
    }

    public static void main(String[] args) {
        OnlyDigits onlyDigits=new OnlyDigits();
        System.out.println(onlyDigits.regex("1234567890"));
    }
}
