package com.asu.image.utils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class Utils {


    public static String getTempPath(String fileName, String userName) {
        return System.getProperty("java.io.tmpdir") + userName.toLowerCase() + "/" + fileName;
    }


    public static String getTempPath(String userName) {
        return System.getProperty("java.io.tmpdir") + userName.toLowerCase() + "/";
    }


    public static String secretKeyToBase64String(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }


    public static Key base64ToSecretKey(String base64KeyString) {
        byte[] keyBytes=Base64.getDecoder().decode(base64KeyString);
        SecretKey key = new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
        return key;
    }
}
