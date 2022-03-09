package Main;

import java.util.Locale;

public class Palindrome {
    /**
     * Time Complexity is O(n) where n is size of string.
     * @param word
     * @return boolean that either the string is palindrome or not
     */
    public boolean palidromeR(String word){
        if(word.equals(null)||word.length()<=1) return true;
        word=word.toLowerCase(Locale.ROOT);
        return paliRecursion(word);
    }

    private boolean paliRecursion(String word){

        if(word.equals(null)||word.length()<=1)
        return true;
        int start=0;
        int end=word.length()-1;
        if(!Character.isLetterOrDigit(word.charAt(start))
                && start<end) return paliRecursion(word.substring(1,word.length()));

        else if(!Character.isLetterOrDigit(word.charAt(end))
                && start<end) return paliRecursion(word.substring(0,word.length()-1));


        if(word.charAt(start)!=word.charAt(end)) return false;
        else{
            return  paliRecursion(word.substring(1,word.length()-1));
        }



    }

    /**
     * Time Complexity is O(n) where n is size of string.
     * @param word
     * @return boolean that either the string is palindrome or not
     */
    public boolean palidromeP(String word){
        word=word.toLowerCase(Locale.ROOT);
        int start=0;
        int end=word.length()-1;
        while(start<end){
            while(!Character.isLetterOrDigit(word.charAt(start))
                   && start<end) start ++;
            while(!Character.isLetterOrDigit(word.charAt(end))
                   && start<end) end --;

            if(word.charAt(start)!=word.charAt(end)) return false;
            else{
                start++;
                end--;
            }

        }
          return  true;
    }

    public static void main(String[] args) {
        Palindrome palindrome=new Palindrome();
        System.out.println(palindrome.palidromeR("A man, a plan, a canal: Panama"));
    }
}
