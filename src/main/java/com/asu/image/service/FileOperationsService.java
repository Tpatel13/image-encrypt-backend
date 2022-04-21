//package com.asu.image.service;
//
//
//import lombok.extern.log4j.Log4j2;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.nio.file.Path;
//
//@Service
//@Log4j2
//public class FileOperationsService {
//
//
//    public Boolean storeFile(MultipartFile fileName) {
//        log.info("Initiating file upload service , File name : {}", file.getName());
//
//
//        try {
//
//            // Check if the file's name contains invalid characters
//            if (fileName.contains("..")) {
//                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
//            }
//
//            // Copy file to the target location (Replacing existing file with the same name)
//            Path targetLocation = this.fileStorageLocation.resolve(fileName);
//
//            log.info("File successfully uploaded , File name : {}", fileName.getName());
//
//            return Boolean.TRUE;
//        } catch (IOException ex) {
//            log.error("Error while writing file to location {}", ex.getMessage());
//            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
//        }
//
//
//    }
//
//
//    public Resource loadFileAsResource(String fileName) {
//        try {
//            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//            if (resource.exists()) {
//                return resource;
//            } else {
//                throw new MyFileNotFoundException("File not found " + fileName);
//            }
//        } catch (MalformedURLException ex) {
//            throw new MyFileNotFoundException("File not found " + fileName, ex);
//        }
//    }
//
//}
