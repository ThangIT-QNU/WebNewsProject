package com.webnewsproject.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class UploadService {
    public static void uploadImage(MultipartFile part, String filename, String uploadDir) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        try {
            InputStream fileInputStream = part.getInputStream();
            Path filePath = uploadPath.resolve(filename);
            Files.copy(fileInputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e){
            throw new IOException("Không thể tải lên tệp hình ảnh "+ filename,e);
        }

    }
}
