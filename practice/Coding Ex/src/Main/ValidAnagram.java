package Main;

import java.util.Arrays;

public class ValidAnagram {
    /**
     * Time Complexity O( nlog(n) )
     * @param s
     * @param t
     * @return boolean that given string(t) anagram of original(s)
     */
    public boolean isAnagramSort(String s, String t) {

        if(s.length()!=t.length()) return false;

        char S[]=s.toCharArray();
        char T[]=t.toCharArray();
        Arrays.sort(S);
        Arrays.sort(T);
        s=new String(S);
        t=new String(T);
        return s.equals(t);

    }
    public boolean isAnagramFaster(String s, String t){
        if(s.length()!=t.length()) return false;

        char counts[]=new char[26];
        for(int i=0;i<s.length();i++){
            counts[s.charAt(i)-'a']++;
            counts[t.charAt(i)-'a']--;
        }

        for(int count: counts ){
            if(count!=0) return false;
        }

        return true;
    }
}
