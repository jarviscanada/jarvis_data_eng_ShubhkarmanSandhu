package Test;

import Main.CompMap;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class CompMapTest {

    @Test
    public void compareMapsEquals1() {
        Map<Integer,String> map1=new HashMap<Integer,String>();
        Map<Integer,String> map2=new HashMap<Integer,String>();
        map1.put(1,"Iron Man");            map2.put(1,"Iron Man");
        map1.put(2,"Captain America");     map2.put(2,"Captain America");
        map1.put(3,"Thor");                map2.put(3,"Thor");
        map1.put(4,"Hulk");                map2.put(4,"Hulk");
        map1.put(5,"Hawk eye");            map2.put(5,"Hawk eye");
        map1.put(6,"Black Widow");         map2.put(6,"Black Widow");

        CompMap compMap=new CompMap();
        assertTrue(compMap.compareMapsEquals(map1,map2));
    }
    @Test
    public void compareMapsEquals2() {
        Map<Integer,String> map1=new HashMap<Integer,String>();
        Map<Integer,String> map2=new HashMap<Integer,String>();
        map1.put(1,"Iron Man");            map2.put(1,"Iron Man");
        map1.put(2,"Captain America");     map2.put(2,"Captain America");
        map1.put(3,"Thor");                map2.put(3,"Thor");
        map1.put(4,"Hulk");                map2.put(4,"Bruce Banner");
        map1.put(5,"Hawk eye");            map2.put(5,"Clint Barton");
        map1.put(6,"Black Widow");         map2.put(6,"Natasha Romanova");

        CompMap compMap=new CompMap();
        assertFalse(compMap.compareMapsEquals(map1,map2));
    }
    @Test
    public void compareMapsEquals3() {
        Map<Integer,String> map1=new HashMap<Integer,String>();
        Map<Integer,String> map2=new HashMap<Integer,String>();
        map1.put(1,"Iron Man");            map2.put(1,"Iron Man");
        map1.put(2,"Captain America");     map2.put(2,"Captain America");
        map1.put(3,"Thor");                map2.put(3,"Thor");
        map1.put(3,"Hulk");                map2.put(3,"Hulk");
        map1.put(4,"Hawk eye");            map2.put(4,"Hawk eye");
        map1.put(2,"Black Widow");         map2.put(2,"Black Widow");

        CompMap compMap=new CompMap();
        assertTrue(compMap.compareMapsEquals(map1,map2));
    }

}