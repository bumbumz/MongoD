package com.example.mongodbn8napp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.mongodbn8napp.model.Branch.Branch;

public interface BranchRepository extends MongoRepository<Branch, String> {
}
