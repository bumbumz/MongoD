package com.example.mongodbn8napp.service.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.example.mongodbn8napp.global.exception.InvalidException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Service
public class ImageUploadService {
    private static final Logger logger = LoggerFactory.getLogger(ImageUploadService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${imgbb.api.key}")
    private String imgbbApiKey;

    public String uploadImage(MultipartFile file) {
        try {
            // Validate file
            if (file == null || file.isEmpty()) {
                throw new InvalidException("File ảnh không được để trống");
            }
            String contentType = file.getContentType();
            if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType)) {
                throw new InvalidException("Chỉ hỗ trợ định dạng JPG hoặc PNG");
            }
            if (file.getSize() > 32 * 1024 * 1024) {
                throw new InvalidException("Kích thước file không được vượt quá 32MB");
            }

            // Log API Key for debugging
            logger.debug("Using ImgBB API Key: {}", imgbbApiKey);

            // Convert to Base64
            logger.debug("Uploading image to ImgBB: {}", file.getOriginalFilename());
            String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("key", imgbbApiKey);
            body.add("image", base64Image);

            // Send request to ImgBB
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "multipart/form-data");
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            String url = "https://api.imgbb.com/1/upload";
            Map response = restTemplate.postForObject(url, requestEntity, Map.class);

            // Process response
            if (response != null && response.containsKey("data")) {
                Map<String, Object> data = (Map<String, Object>) response.get("data");
                String imageUrl = (String) data.get("url");
                logger.info("Image uploaded successfully: {}", imageUrl);
                return imageUrl;
            } else {
                logger.error("Failed to upload image to ImgBB: Invalid response");
                throw new InvalidException("Tải ảnh lên ImgBB thất bại");
            }
        } catch (ResourceAccessException e) {
            logger.error("Connection error to ImgBB: {}", e.getMessage());
            throw new InvalidException("Lỗi kết nối tới ImgBB: " + e.getMessage());
        } catch (IOException e) {
            logger.error("Error uploading image to ImgBB: {}", e.getMessage());
            throw new InvalidException("Lỗi khi tải ảnh lên: " + e.getMessage());
        }
    }
}