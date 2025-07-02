package com.example.mongodbn8napp.service.branch;

import org.springframework.data.domain.Pageable;

import com.example.mongodbn8napp.model.Branch.Branch;

import java.util.Optional;

public interface BranchService {
    Branch createBranch(Branch branch);
    Optional<Branch> getBranchById(String id);
    Branch updateBranch(String id, Branch branch);
    boolean deleteBranch(String id);
    com.example.mongodbn8napp.global.ApiResponse getAllBranches(Pageable pageable);
}