package com.example.mongodbn8napp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.mongodbn8napp.model.banner.Banner;

public interface BannerRepository extends MongoRepository<Banner, String> {
}