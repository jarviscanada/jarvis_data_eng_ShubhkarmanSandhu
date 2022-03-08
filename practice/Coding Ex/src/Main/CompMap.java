package Main;

import java.util.HashMap;
import java.util.Map;

public class CompMap {
    public <K,V> boolean compareMapsEquals(Map<K,V> m1, Map<K,V> m2){
             return m1.equals(m2);
    }

    public static void main(String[] args) {
        Map<Integer,String> map1=new HashMap<Integer,String>();
        Map<Integer,String> map2=new HashMap<Integer,String>();
        map1.put(1,"Iron Man");            map2.put(1,"Iron Man");
        map1.put(2,"Captain America");     map2.put(2,"Captain America");
        map1.put(3,"Thor");                map2.put(3,"Thor");
        map1.put(4,"Hulk");                map2.put(4,"Hulk");
        map1.put(5,"Hawk eye");            map2.put(5,"Hawk eye");
        map1.put(6,"Black Widow");         map2.put(6,"Black Widow");

        CompMap compMap=new CompMap();
        System.out.println(compMap.compareMapsEquals(map1,map2));
    }

}
