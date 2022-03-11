package Main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RepeatedChar {
    public List solution(String str){
        Map<Character,Integer> map=new HashMap<>();
        char array[]=str.toCharArray();
        for(char c:array){
            if(map.containsKey(c)){
                map.put(c,map.get(c)+1);
            }
            else{
                map.put(c,1);
            }
        }
        Set<Map.Entry<Character,Integer>> set=map.entrySet();
        List<Character> collect = set.stream()
                                     .filter(e -> e.getValue() > 1)
                                     .map(e -> e.getKey())
                                     .collect(Collectors.toList());
       return collect;

    }

    public static void main(String[] args) {
        RepeatedChar repeatedChar= new RepeatedChar();
        System.out.println(repeatedChar.solution("Shubhkarman").toString());
    }
}
