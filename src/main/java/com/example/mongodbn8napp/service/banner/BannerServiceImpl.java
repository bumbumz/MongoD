package com.example.mongodbn8napp.service.banner;

import com.example.mongodbn8napp.global.ApiResponse;
import com.example.mongodbn8napp.global.exception.InvalidException;
import com.example.mongodbn8napp.global.exception.NotFoundException;
import com.example.mongodbn8napp.model.banner.Banner;
import com.example.mongodbn8napp.repository.BannerRepository;
import com.example.mongodbn8napp.service.image.ImageUploadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private ImageUploadService imageUploadService;

    @Override
    public Banner createBanner(Banner banner, MultipartFile thumbnail) {
        // Validation
        if (banner.getName() == null || banner.getName().isBlank()) {
            throw new InvalidException("Tên banner không được để trống");
        }
        if (thumbnail == null) {
            throw new InvalidException("Ảnh thumbnail không được để trống");
        }
        if (banner.getOrder() < 0) {
            throw new InvalidException("Thứ tự banner không được âm");
        }

        // Upload image
        String thumbnailUrl = imageUploadService.uploadImage(thumbnail);
        banner.setThumbnailUrl(thumbnailUrl);

        banner.setCreatedAt(LocalDateTime.now());
        banner.setUpdatedAt(LocalDateTime.now());
        return bannerRepository.save(banner);
    }

    @Override
    public ApiResponse getAllBanners(Pageable pageable) {
        Page<Banner> bannerPage = bannerRepository.findAll(pageable);
        ApiResponse.Pagination pagination = new ApiResponse.Pagination(
                bannerPage.getNumber(),
                bannerPage.getSize(),
                bannerPage.getTotalElements(),
                bannerPage.getTotalPages());
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Lấy danh sách banner thành công",
                bannerPage.getContent(),
                pagination);
    }

    @Override
    public Optional<Banner> getBannerById(String id) {
        Optional<Banner> banner = bannerRepository.findById(id);
        if (banner.isEmpty()) {
            throw new NotFoundException("Không tìm thấy banner với ID: " + id);
        }
        return banner;
    }

    @Override
    public Banner updateBanner(String id, Banner banner, MultipartFile thumbnail) {
        Optional<Banner> existingBanner = bannerRepository.findById(id);
        if (existingBanner.isEmpty()) {
            throw new NotFoundException("Không tìm thấy banner với ID: " + id);
        }
        // Validation
        if (banner.getName() == null || banner.getName().isBlank()) {
            throw new InvalidException("Tên banner không được để trống");
        }
        if (banner.getOrder() < 0) {
            throw new InvalidException("Thứ tự banner không được âm");
        }

        Banner updatedBanner = existingBanner.get();
        updatedBanner.setName(banner.getName());
        updatedBanner.setLink(banner.getLink());
        updatedBanner.setDescription(banner.getDescription());
        updatedBanner.setOrder(banner.getOrder());
        updatedBanner.setActive(banner.isActive());
        updatedBanner.setUpdatedAt(LocalDateTime.now());

        // Upload new image if provided
        if (thumbnail != null) {
            String thumbnailUrl = imageUploadService.uploadImage(thumbnail);
            updatedBanner.setThumbnailUrl(thumbnailUrl);
        }

        return bannerRepository.save(updatedBanner);
    }

    @Override
    public boolean deleteBanner(String id) {
        if (!bannerRepository.existsById(id)) {
            throw new NotFoundException("Không tìm thấy banner với ID: " + id);
        }
        bannerRepository.deleteById(id);
        return true;
    }

    @Override
    public void deleteAllBanners() {
        bannerRepository.deleteAll();
    }
}