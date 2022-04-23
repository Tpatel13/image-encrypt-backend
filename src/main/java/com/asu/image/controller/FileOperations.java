package com.asu.image.controller;

import com.asu.image.model.Login;
import com.asu.image.service.MongoDbService;
import com.asu.image.service.StorageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Log4j2
public class FileOperations {

    @Autowired
    MongoDbService mongoDbService;

    @Autowired
    StorageService storageService;


//    @PostMapping("/uploadFile")
//    public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("user") String username) throws IOException {
//        try {
//            Key key= storageService.storeAndEncrypt(file, username);
//
//            log.info("File Successfully saved");
//            return key.getEncoded().toString();
//        } catch (Exception e) {
//            log.error("Error while saving file {}", e.getMessage());
//            return "File Not Uploaded";
//        }
//    }


    @PostMapping("/uploadEncryptFile")
    public String encryptFile(@RequestParam("file") MultipartFile file, @RequestParam("user") String username) throws IOException {
        try {
            String key = storageService.storeAndEncrypt(file, username);
            updateUserData(username, key);
            log.info("File Successfully encrypted and saved");
            return key;
        } catch (Exception e) {
            log.error("Error while saving file {}", e.getMessage());
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @PostMapping("/getDecryptedFile")
    public String decryptFile(@RequestParam("key") String key, @RequestParam("user") String username, @RequestParam("image") String imageName) throws IOException {
        try {
            String decryptedFile = storageService.decryptImage(key, username, imageName);

            log.info("File Successfully decrypted and uploaded to {}", decryptedFile);
            return decryptedFile;
        } catch (Exception e) {
            log.error("Error while saving file {}", e.getMessage());
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @GetMapping("/getAll")
    public Object getALlUsers() {
        return mongoDbService.findAll();
    }


    private void updateUserData(String username, String key) {
        Login originalUserInfo = mongoDbService.findItemByName(username);
        originalUserInfo.setEncryptionKey(key);
        mongoDbService.save(originalUserInfo);

    }
}
