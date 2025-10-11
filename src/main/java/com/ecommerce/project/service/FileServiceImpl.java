package com.ecommerce.project.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String uploadDir, MultipartFile image) throws IOException {
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));

        if (originalFileName.contains("..")) {
            throw new IOException("Filename contains invalid path sequence: " + originalFileName);
        }

        String extension = "";
        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex >= 0) {
            extension = originalFileName.substring(dotIndex).toLowerCase();
        }

        String randomId = UUID.randomUUID().toString();
        String fileName = randomId + extension;

        Path dirPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(dirPath);

        Path targetPath = dirPath.resolve(fileName);

        try (InputStream is = image.getInputStream()) {
            Path tempFile = Files.createTempFile(dirPath, "upload-", ".tmp");
            try {
                Files.copy(is, tempFile, StandardCopyOption.REPLACE_EXISTING);
                try {
                    Files.move(tempFile, targetPath, StandardCopyOption.ATOMIC_MOVE,
                            StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException ae) {
                    Files.move(tempFile, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }
            } finally {
                Files.deleteIfExists(tempFile);
            }
        }

        return fileName;
    }
}
