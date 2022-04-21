package com.asu.image.utils;

public class Utils {


    public static String getTempPath(String fileName, String userName) {
        return System.getProperty("java.io.tmpdir") + userName.toLowerCase() + "/" + fileName;
    }


    public static String getTempPath(String userName) {
        return System.getProperty("java.io.tmpdir") + userName.toLowerCase() + "/";
    }

}
