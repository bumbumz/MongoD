package com.example.mongodbn8napp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mongodbn8napp.dto.BannerResponseDTO;
import com.example.mongodbn8napp.dto.request.BannerRequestDTO;
import com.example.mongodbn8napp.face.banner.BannerFacade;
import com.example.mongodbn8napp.global.ApiResponse;
import com.example.mongodbn8napp.model.banner.Banner;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/banners")
@Validated
public class BannerController {

    @Autowired
    private BannerFacade bannerFacade;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<BannerResponseDTO>> createBanner(
            @Valid @ModelAttribute BannerRequestDTO bannerRequestDTO) {
        Banner banner = new Banner();
        banner.setName(bannerRequestDTO.getName());
        banner.setLink(bannerRequestDTO.getLink());
        banner.setDescription(bannerRequestDTO.getDescription());
        banner.setOrder(bannerRequestDTO.getOrder());
        banner.setActive(bannerRequestDTO.getIsActive());
        ApiResponse<BannerResponseDTO> response = bannerFacade.createBanner(banner, bannerRequestDTO.getThumbnail());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllBanners(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ApiResponse response = bannerFacade.getAllBanners(pageable);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BannerResponseDTO>> getBannerById(@PathVariable("id") String id) {
        ApiResponse<BannerResponseDTO> response = bannerFacade.getBannerById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<BannerResponseDTO>> updateBanner(@PathVariable("id") String id,
            @Valid @ModelAttribute BannerRequestDTO bannerRequestDTO) {
        Banner banner = new Banner();
        banner.setName(bannerRequestDTO.getName());
        banner.setLink(bannerRequestDTO.getLink());
        banner.setDescription(bannerRequestDTO.getDescription());
        banner.setOrder(bannerRequestDTO.getOrder());
        banner.setActive(bannerRequestDTO.getIsActive());
        ApiResponse<BannerResponseDTO> response = bannerFacade.updateBanner(id, banner,
                bannerRequestDTO.getThumbnail());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBanner(@PathVariable("id") String id) {
        ApiResponse<Void> response = bannerFacade.deleteBanner(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteAllBanners() {
        ApiResponse<Void> response = bannerFacade.deleteAllBanners();
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}