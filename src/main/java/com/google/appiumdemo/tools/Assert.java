package com.google.appiumdemo.tools;

/**
 *
 */
public class Assert {

    /**
     * in case A equals B return ture else return false
     * @param a
     * @param b
     * @return
     */
    public static boolean assertEquals(String a, String b) {
        if (a.equals(b)) {
            return true;
        } else {
            return false;
        }
    }

}
