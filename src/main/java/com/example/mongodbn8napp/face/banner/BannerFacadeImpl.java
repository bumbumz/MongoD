package com.example.mongodbn8napp.face.banner;

import com.example.mongodbn8napp.dto.BannerResponseDTO;
import com.example.mongodbn8napp.global.ApiResponse;
import com.example.mongodbn8napp.model.banner.Banner;
import com.example.mongodbn8napp.service.banner.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BannerFacadeImpl implements BannerFacade {

    @Autowired
    private BannerService bannerService;

    private BannerResponseDTO convertToDTO(Banner banner) {
        BannerResponseDTO dto = new BannerResponseDTO();
        dto.setId(banner.getId());
        dto.setName(banner.getName());
        dto.setThumbnailUrl(banner.getThumbnailUrl());
        dto.setLink(banner.getLink());
        dto.setDescription(banner.getDescription());
        dto.setOrder(banner.getOrder());
        dto.setActive(banner.isActive());
        dto.setCreatedAt(banner.getCreatedAt());
        dto.setUpdatedAt(banner.getUpdatedAt());
        return dto;
    }

    @Override
    public ApiResponse<BannerResponseDTO> createBanner(Banner banner, MultipartFile thumbnail) {
        Banner savedBanner = bannerService.createBanner(banner, thumbnail);
        BannerResponseDTO dto = convertToDTO(savedBanner);
        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Tạo banner thành công",
                dto,
                null
        );
    }

    @Override
    public ApiResponse getAllBanners(Pageable pageable) {
        ApiResponse<List<Banner>> serviceResponse = bannerService.getAllBanners(pageable);
        List<BannerResponseDTO> dtos = serviceResponse.getData().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ApiResponse<>(
                serviceResponse.getStatus(),
                serviceResponse.getMessage(),
                dtos,
                serviceResponse.getPagination()
        );
    }

    @Override
    public ApiResponse<BannerResponseDTO> getBannerById(String id) {
        Banner banner = bannerService.getBannerById(id).orElseThrow(
                () -> new RuntimeException("Không tìm thấy banner với ID: " + id)
        );
        BannerResponseDTO dto = convertToDTO(banner);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Lấy banner thành công",
                dto,
                null
        );
    }

    @Override
    public ApiResponse<BannerResponseDTO> updateBanner(String id, Banner banner, MultipartFile thumbnail) {
        Banner updatedBanner = bannerService.updateBanner(id, banner, thumbnail);
        BannerResponseDTO dto = convertToDTO(updatedBanner);
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Cập nhật banner thành công",
                dto,
                null
        );
    }

    @Override
    public ApiResponse<Void> deleteBanner(String id) {
        bannerService.deleteBanner(id);
        return new ApiResponse<>(
                HttpStatus.NO_CONTENT.value(),
                "Xóa banner thành công",
                null,
                null
        );
    }

    @Override
    public ApiResponse<Void> deleteAllBanners() {
        bannerService.deleteAllBanners();
        return new ApiResponse<>(
                HttpStatus.NO_CONTENT.value(),
                "Xóa tất cả banner thành công",
                null,
                null
        );
    }
}