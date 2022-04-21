package com.asu.image.service;


import com.asu.image.utils.Utils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Log4j2
public class StorageService {


    public void store(MultipartFile file, String username) throws IOException {

        String filePath = Utils.getTempPath(username);
        log.info("Storing file at {}", filePath);
        Path path = Paths.get(filePath);
        path.toFile().mkdir();
        File targetFile = new File(path.toString() + "/" + file.getOriginalFilename());

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            log.info("File Successfully stored at {}", targetFile.getAbsolutePath());
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: {}" + filePath);
        }

    }
}
