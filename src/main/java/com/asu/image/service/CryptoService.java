package com.asu.image.service;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.security.*;

@Service
@Log4j2
public class CryptoService {


    public void encryptFile(){

    }

    public void decryptFile(){

    }


    public KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        return kp;
    }


}
