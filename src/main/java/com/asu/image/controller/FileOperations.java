package com.asu.image.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Log4j2
public class FileOperations {


    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file)
    {


    }
}
