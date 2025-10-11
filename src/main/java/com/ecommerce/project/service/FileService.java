package com.ecommerce.project.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String uploadImage(String uploadDir, MultipartFile image) throws IOException;
}
