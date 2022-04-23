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
import java.util.UUID;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@Log4j2
public class StorageService {

    @Autowired
    CryptoService cryptoService;

    public StorageService() throws NoSuchAlgorithmException {
    }


    public String storeAndEncrypt(MultipartFile file, String username) throws Exception {
        //Generating path to store everything
        String filePath = Utils.getTempPath(username);
        log.info("Storing file at {}", filePath);
        Path path = Paths.get(filePath);

        //Store Image in original format for backup
        storeBackupImage(file, path);

        String base64keyString = cryptoService.generateCipher();

        // Save image in encrypted form
        String encryptedFilePath = saveEncryptedFile(Utils.base64ToSecretKey(base64keyString), file, path, username);
        //decryptimage from encrypted path

        return base64keyString;

    }


    public String decryptImage(String base64keyString, String user, String imageName) throws IOException {
        File sourceEncryptedFile = new File(Utils.getTempPath(user) + "/encryptedFiles/" + imageName);

        return decryptImageAndSave(Utils.base64ToSecretKey(base64keyString), sourceEncryptedFile.getAbsolutePath(), Utils.getTempPath(user));
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

    private String saveEncryptedFile(Key key, MultipartFile file, Path path, String user) throws IOException {
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

        return targetEncryptedFile.getAbsolutePath();

    }


    private String decryptImageAndSave(Key key, String encryptedFilePath, String userName) throws IOException {

        log.info("Decrypting file from : {}", encryptedFilePath);
        String decryptedFileName = UUID.randomUUID().toString();

        try {
            File file = new File(encryptedFilePath);
            byte[] encryptedBytes = Files.readAllBytes(Paths.get(encryptedFilePath));
            byte[] decryptedBytes = cryptoService.decryptImageFile(key, encryptedBytes);


            File targetDecrptFilePath = new File(userName + "/decryptedFiles/" + decryptedFileName);

            File targetEncryptedFileFolder = new File(userName + "/decryptedFiles/");


            try (InputStream inputStream = new ByteArrayInputStream(decryptedBytes)) {

                Files.createDirectories(targetEncryptedFileFolder.toPath());
                Files.copy(inputStream, targetDecrptFilePath.toPath(), REPLACE_EXISTING);
                log.info("File decryptedFiles Successfully stored at {}", targetDecrptFilePath.getAbsolutePath());
            } catch (IOException ioe) {
                ioe.printStackTrace();
                log.error("Error while saving file in decryptedFiles form at {}", targetDecrptFilePath.getAbsolutePath());
                throw new IOException("Could not save  decryptedFiles image file: {}" + targetDecrptFilePath.getAbsolutePath());
            }
            log.info("File Decrypted Successfully from  stored at {}", encryptedFilePath);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            log.error("Error while decrypting form at {}", encryptedFilePath);
            throw new IOException("Could not save image file: " + encryptedFilePath);
        }
        return decryptedFileName;

    }

}
