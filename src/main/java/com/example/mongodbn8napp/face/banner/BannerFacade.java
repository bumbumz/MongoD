package com.example.mongodbn8napp.face.banner;

import com.example.mongodbn8napp.dto.BannerResponseDTO;
import com.example.mongodbn8napp.global.ApiResponse;
import com.example.mongodbn8napp.model.banner.Banner;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface BannerFacade {
    ApiResponse<BannerResponseDTO> createBanner(Banner banner, MultipartFile thumbnail);
    ApiResponse getAllBanners(Pageable pageable);
    ApiResponse<BannerResponseDTO> getBannerById(String id);
    ApiResponse<BannerResponseDTO> updateBanner(String id, Banner banner, MultipartFile thumbnail);
    ApiResponse<Void> deleteBanner(String id);
    ApiResponse<Void> deleteAllBanners();
    
}