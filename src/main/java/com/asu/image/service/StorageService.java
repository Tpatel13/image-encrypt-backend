package com.asu.image.service;


import com.asu.image.utils.Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.*;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@Log4j2
public class StorageService {

    @Autowired
    CryptoService cryptoService;

    public void storeAndEncrypt(MultipartFile file, String username) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {

        String filePath = Utils.getTempPath(username);
        log.info("Storing file at {}", filePath);


        Path path = Paths.get(filePath);
        path.toFile().mkdir();
        File targetFile = new File(path.toString() + "/" + file.getOriginalFilename());

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, targetFile.toPath(), REPLACE_EXISTING);
            log.info("File Backup Successfully stored at {}", targetFile.getAbsolutePath());
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: {}" + filePath);
        }


        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        Key key = keyGenerator.generateKey();

        byte[] encryptedBytes = cryptoService.encryptPdfFile(key,file.getBytes());


        File targetEncryptedFile = new File(path.toString() + "/encryptedFiles/" + file.getOriginalFilename());
        File targetEncryptedFileFolder = new File(path.toString() + "/encryptedFiles/");


        try (InputStream inputStream = new ByteArrayInputStream(encryptedBytes)) {
            Files.createDirectories(targetEncryptedFileFolder.toPath());


            Files.copy(inputStream,targetEncryptedFile.toPath(),REPLACE_EXISTING);
            log.info("File Backup Successfully stored at {}", targetEncryptedFile.getAbsolutePath());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new IOException("Could not save image file: {}" + targetFile.getAbsolutePath());
        }



//        try {
//            assert encryptedBytes != null;
//            try (InputStream inputStream = new ByteArrayInputStream(encryptedBytes)) {
//                Files.copy(inputStream, targetEncryptedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//                log.info("Encrypted File Successfully stored at {}", targetFile.getAbsolutePath());
//            }
//
//            byte[] decryptedBytes = decrypt(encryptedBytes, privateKey);
//            File targetDecryptedFile = new File(path.toString() + "/encryptedFiles/" + file.getOriginalFilename());
//
//            try {
//                assert decryptedBytes != null;
//                try (InputStream inputStream = new ByteArrayInputStream(decryptedBytes)) {
//                    Files.copy(inputStream, targetDecryptedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//                    log.info("Decrypted File Successfully stored at {}", targetFile.getAbsolutePath());
//                }
//            } catch (IOException ioe) {
//                throw new IOException("image decryption failed: {}" + filePath);
//            }
//
//        } catch (IOException ioe) {
//            throw new IOException("Image Encryption save image file: {}" + filePath);
//        }



    }
}
