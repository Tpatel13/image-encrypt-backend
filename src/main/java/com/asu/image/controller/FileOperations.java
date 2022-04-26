package com.asu.image.controller;

import com.asu.image.model.Login;
import com.asu.image.model.Response;
import com.asu.image.service.MongoDbService;
import com.asu.image.service.StorageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;

@RestController
@Log4j2
//@CrossOrigin(maxAge = 36000)
@CrossOrigin(origins = "http://localhost:3000")
public class FileOperations {

    @Autowired
    MongoDbService mongoDbService;

    @Autowired
    StorageService storageService;


    @PostMapping(value = "/uploadFile", produces = {"application/json"})
    public Response encryptFile(@RequestParam("file") MultipartFile file, @RequestParam("user") String username) throws IOException {
        Response response = new Response();
        try {
            String key = storageService.storeAndEncrypt(file, username);
            updateUserData(username, key);
            log.info("File Successfully encrypted and saved with {} key ", key);
            response.setMessage(key);
            return response;
        } catch (Exception e) {
            log.error("Error while saving file {}", e.getMessage());
            e.printStackTrace();
            response.setMessage("Error while saving file");
            return response;
        }
    }

    @GetMapping(value = "/getDecryptedFile", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response decryptFile(@RequestParam("key") String key, @RequestParam("user") String username, @RequestParam("image") String imageName) throws IOException {

        Response response = new Response();
        try {
            imageName = imageName.trim();
            key = key.trim();
            Boolean validate = Boolean.TRUE.equals(validateKey(username, key));
            if (validate) {
                String decryptedFile = storageService.decryptImage(key, username, imageName);
                log.info("File Successfully decrypted and uploaded to {}", decryptedFile);
                response.setMessage(decryptedFile);
                return response;
            }
            log.error("Invilid Combination to decrypt of key and image name");
            response.setMessage("false");
            return response;
        } catch (Exception e) {
            log.error("Error while saving file {}", e.getMessage());
            e.printStackTrace();
            response.setMessage("false");
            return response;
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

    private Boolean validateKey(String username, String inputKey) {
        Login originalUserInfo = mongoDbService.findItemByName(username);
        Boolean compare = originalUserInfo.getEncryptionKey().equals(inputKey.trim());
        if (compare) {
            return true;
        }
        return false;
    }
}
