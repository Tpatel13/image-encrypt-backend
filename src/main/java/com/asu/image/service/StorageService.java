package com.asu.image.service;


import com.asu.image.utils.Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@Log4j2
public class StorageService {

    @Autowired
    CryptoService cryptoService;

    public StorageService() throws NoSuchAlgorithmException {
    }

    public Key storeAndEncrypt(MultipartFile file, String username) throws Exception {
        //Generating path to store everything
        String filePath = Utils.getTempPath(username);
        log.info("Storing file at {}", filePath);
        Path path = Paths.get(filePath);

        //Store Image in original format for backup
        storeBackupImage(file, path);

        Key key = cryptoService.generateCipher();

        // Save image in encrypted form
        saveEncryptedFile(key, file, path);

        return key;

    }


    private void storeBackupImage(MultipartFile file, Path path) throws Exception {


        path.toFile().mkdir();
        File targetFile = new File(path.toString() + "/" + file.getOriginalFilename());

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, targetFile.toPath(), REPLACE_EXISTING);
            log.info("File Backup Successfully stored at {}", targetFile.getAbsolutePath());
        } catch (IOException ioe) {
            log.error("Error while saving backup image file at {}", targetFile.getAbsolutePath());
            ioe.printStackTrace();
            throw new Exception();
        }

    }

    private void saveEncryptedFile(Key key, MultipartFile file, Path path) throws IOException {
        byte[] encryptedBytes = cryptoService.encryptImageFile(key, file.getBytes());

        File targetEncryptedFile = new File(path.toString() + "/encryptedFiles/" + file.getOriginalFilename());
        File targetEncryptedFileFolder = new File(path.toString() + "/encryptedFiles/");


        try (InputStream inputStream = new ByteArrayInputStream(encryptedBytes)) {
            Files.createDirectories(targetEncryptedFileFolder.toPath());
            Files.copy(inputStream, targetEncryptedFile.toPath(), REPLACE_EXISTING);
            log.info("File Backup Successfully stored at {}", targetEncryptedFile.getAbsolutePath());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            log.error("Error while saving file in encrypted form at {}", targetEncryptedFile.getAbsolutePath());
            throw new IOException("Could not save image file: {}" + targetEncryptedFile.getAbsolutePath());
        }

    }

}
