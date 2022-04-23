package com.asu.image.service;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

@Service
@Log4j2
public class CryptoService {


    public void encryptFile() {


    }


    public byte[] encryptImageFile(Key key, byte[] content) {
        Cipher cipher;
        byte[] encrypted = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypted = cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while encrypting image {}", e.getMessage());
        }
        return encrypted;

    }


    public Key generateCipher() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        Key key = keyGenerator.generateKey();
        return key;
    }

    public byte[] decryptImageFile(Key key, byte[] textCryp) {
        Cipher cipher;
        byte[] decrypted = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypted = cipher.doFinal(textCryp);

        } catch (Exception e) {
            log.error("Error while decrypting image {}", e.getMessage());

            e.printStackTrace();
        }

        return decrypted;
    }


}
