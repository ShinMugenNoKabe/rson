/*
 * Copyright (c) Rufino Serrano. All rights reserved.
 */

package rson;

/**
 *
 * @author Rufino Serrano Cañas
 */
public class ObjectTest {
    private int testObjectInt;
    private String testObjectStr;

    public ObjectTest(int testObjectInt, String testObjectStr) {
        this.testObjectInt = testObjectInt;
        this.testObjectStr = testObjectStr;
    }

    public int getTestObjectInt() {
        return testObjectInt;
    }

    public void setTestObjectInt(int testObjectInt) {
        this.testObjectInt = testObjectInt;
    }

    public String getTestObjectStr() {
        return testObjectStr;
    }

    public void setTestObjectStr(String testObjectStr) {
        this.testObjectStr = testObjectStr;
    }
    
}
