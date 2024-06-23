package com.spring_boot_dated_upload_storage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Base64;

@Service
public class UploadService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private Path getUploadPath() {
        LocalDate today = LocalDate.now();
        String month = String.format("%02d", today.getMonthValue());
        String day = String.format("%02d", today.getDayOfMonth());
        return Paths.get(uploadDir, String.valueOf(today.getYear()), month, day);
    }

    public String storeFile(String fileName, String base64) throws IOException {
        byte[] data = Base64.getDecoder().decode(base64);
        Path uploadPath = getUploadPath();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, data);

        return filePath.toString();
    }
}