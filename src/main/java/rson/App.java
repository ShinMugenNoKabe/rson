package rson;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        ClassTest test = new ClassTest();
        test.setIntegerTest(1);
        test.setStringTest(null);
        test.setBooleanTest(true);
        
        ArrayList<ObjectTest> list = new ArrayList<>();
        list.add(new ObjectTest(1, "Obj2"));
        list.add(new ObjectTest(2, "Obj3"));
        list.add(new ObjectTest(3, "Obj4"));
        
        test.setListTest(list);
        
        test.setObjectTest(new ObjectTest(4, "Obj5"));
        
        HashMap<ObjectTest, String> map = new HashMap<>();
        map.put(new ObjectTest(5, "Obj6"), "test");
        test.setMapTest(map);

        System.out.println(Rson.stringify(test));
        System.out.println(Rson.stringifyPretty(test));
        
    }
}
