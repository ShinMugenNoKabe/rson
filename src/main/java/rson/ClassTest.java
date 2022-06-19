package rson;

import java.util.ArrayList;
import java.util.HashMap;

@SerializeRson
public class ClassTest {

    private String stringTest;
    private Integer integerTest;
    private ArrayList<ObjectTest> listTest;
    private ObjectTest objectTest;
    private Boolean booleanTest;
    private HashMap<ObjectTest, String> mapTest;

    public String getStringTest() {
        return stringTest;
    }

    public void setStringTest(String stringTest) {
        this.stringTest = stringTest;
    }

    public Integer getIntegerTest() {
        return integerTest;
    }

    public void setIntegerTest(Integer integerTest) {
        this.integerTest = integerTest;
    }

    public ArrayList<ObjectTest> getListTest() {
        return listTest;
    }

    public void setListTest(ArrayList<ObjectTest> listTest) {
        this.listTest = listTest;
    }

    public ObjectTest getObjectTest() {
        return objectTest;
    }

    public void setObjectTest(ObjectTest objectTest) {
        this.objectTest = objectTest;
    }

    public Boolean getBooleanTest() {
        return booleanTest;
    }

    public void setBooleanTest(Boolean booleanTest) {
        this.booleanTest = booleanTest;
    }

    public HashMap<ObjectTest, String> getMapTest() {
        return mapTest;
    }

    public void setMapTest(HashMap<ObjectTest, String> mapTest) {
        this.mapTest = mapTest;
    }
    
}
