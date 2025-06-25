package com.example.mongodbn8napp.service.banner;


import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.example.mongodbn8napp.global.ApiResponse;
import com.example.mongodbn8napp.model.banner.Banner;

import java.util.Optional;

public interface BannerService {
    Banner createBanner(Banner banner, MultipartFile thumbnail);
    ApiResponse getAllBanners(Pageable pageable);
    Optional<Banner> getBannerById(String id);
    Banner updateBanner(String id, Banner banner, MultipartFile thumbnail);
    boolean deleteBanner(String id);
    void deleteAllBanners();
}