package com.ansh.ChatVault_Backend.Service;

import com.ansh.ChatVault_Backend.Exceptions.CustomExceptions.FileUploadError;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileService {

    private final Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) {
        try {
            // Using string-based transformation parameters for more reliable processing
            Map<String, Object> options = ObjectUtils.asMap(
                    "transformation", "w_800,h_800,c_limit,q_auto"
            );

            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
            return uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new FileUploadError("Failed to upload file", e);
        }
    }
}