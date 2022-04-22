package com.asu.image.controller;

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



    @PostMapping("/uploadFile")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("user") String username) throws IOException {
        try {
            storageService.storeAndEncrypt(file, username);
            log.info("File Successfully saved");
            return "OK";
        } catch (Exception e) {
            log.error("Error while saving file {}", e.getMessage());
            return "File Not Uploaded";
        }
    }


    @PostMapping("/encryptFile")
    public String encryptFile(@RequestParam("file") MultipartFile file, @RequestParam("user") String username) throws IOException {
        try {
            storageService.storeAndEncrypt(file, username);
            log.info("File Successfully saved");

            return "OK";
        } catch (Exception e) {
            log.error("Error while saving file {}", e.getMessage());
            return "Not OK";
        }
    }


    @GetMapping("/getAll")
    public Object getALlUsers() {
        return mongoDbService.findAll();
    }


}
