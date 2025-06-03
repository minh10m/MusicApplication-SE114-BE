package com.music.application.be.modules.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    // Phương thức upload từ MultipartFile
    public String uploadFile(MultipartFile file, String resourceType) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        File convFile = convertMultiPartToFile(file);
        return uploadFile(convFile, resourceType);
    }

    // Phương thức upload từ File (dùng lại file tạm)
    public String uploadFile(File file, String resourceType) throws IOException {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("File does not exist: " + (file != null ? file.getAbsolutePath() : "null"));
        }

        try {
            Map uploadResult = cloudinary.uploader().upload(file,
                    ObjectUtils.asMap("resource_type", resourceType));
            return (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            throw new IOException("Failed to upload file to Cloudinary: " + e.getMessage(), e);
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        // Sử dụng UUID để tạo tên file duy nhất
        String tempFileName = "upload_" + UUID.randomUUID().toString() + "." +
                getFileExtension(file.getOriginalFilename());
        File convFile = new File(System.getProperty("java.io.tmpdir"), tempFileName);

        // Kiểm tra và tạo thư mục nếu không tồn tại
        File parentDir = convFile.getParentFile();
        if (!parentDir.exists() && !parentDir.mkdirs()) {
            throw new IOException("Failed to create temporary directory: " + parentDir.getAbsolutePath());
        }

        // Tạo file trước khi ghi
        if (!convFile.createNewFile() && !convFile.exists()) {
            throw new IOException("Failed to create temporary file: " + convFile.getAbsolutePath());
        }

        // Ghi dữ liệu vào file
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            byte[] bytes = file.getBytes();
            fos.write(bytes);
            fos.flush();
        }

        // Kiểm tra file sau khi ghi
        if (!convFile.exists()) {
            throw new IOException("Temporary file does not exist after writing: " + convFile.getAbsolutePath());
        }
        if (!convFile.canRead()) {
            throw new IOException("Cannot read temporary file: " + convFile.getAbsolutePath());
        }

        System.out.println("Temporary file created at: " + convFile.getAbsolutePath());
        return convFile;
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return "tmp";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }
}