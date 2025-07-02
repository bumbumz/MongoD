package com.example.mongodbn8napp.service.branch;

import com.example.mongodbn8napp.global.exception.InvalidException;
import com.example.mongodbn8napp.global.ApiResponse;
import com.example.mongodbn8napp.global.exception.NotFoundException;
import com.example.mongodbn8napp.model.Branch.Branch;
import com.example.mongodbn8napp.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Override
    public Branch createBranch(Branch branch) {
        if (branch.getCity() == null || branch.getCity().isBlank()) {
            throw new InvalidException("Tên thành phố không được để trống");
        }
        if (branch.getProvince() == null || branch.getProvince().isBlank()) {
            throw new InvalidException("Tên tỉnh không được để trống");
        }
        if (branch.getAddress() == null || branch.getAddress().isBlank()) {
            throw new InvalidException("Địa chỉ không được để trống");
        }
        branch.setDateAdd(LocalDateTime.now());
        branch.setDateUpdate(LocalDateTime.now());
        return branchRepository.save(branch);
    }

    @Override
    public Optional<Branch> getBranchById(String id) {
        Optional<Branch> branch = branchRepository.findById(id);
        if (branch.isEmpty()) {
            throw new NotFoundException("Không tìm thấy chi nhánh với ID: " + id);
        }
        return branch;
    }

    @Override
    public Branch updateBranch(String id, Branch branch) {
        Optional<Branch> existingBranchOpt = branchRepository.findById(id);
        if (existingBranchOpt.isEmpty()) {
            throw new NotFoundException("Không tìm thấy chi nhánh với ID: " + id);
        }
        Branch existingBranch = existingBranchOpt.get();

        if (branch.getCity() != null) {
            if (branch.getCity().isBlank()) {
                throw new InvalidException("Tên thành phố không được để trống");
            }
            existingBranch.setCity(branch.getCity());
        }

        if (branch.getProvince() != null) {
            if (branch.getProvince().isBlank()) {
                throw new InvalidException("Tên tỉnh không được để trống");
            }
            existingBranch.setProvince(branch.getProvince());
        }

        if (branch.getAddress() != null) {
            if (branch.getAddress().isBlank()) {
                throw new InvalidException("Địa chỉ không được để trống");
            }
            existingBranch.setAddress(branch.getAddress());
        }

        existingBranch.setDateUpdate(LocalDateTime.now());
        return branchRepository.save(existingBranch);
    }

    @Override
    public boolean deleteBranch(String id) {
        if (!branchRepository.existsById(id)) {
            throw new NotFoundException("Không tìm thấy chi nhánh với ID: " + id);
        }
        branchRepository.deleteById(id);
        return true;
    }

    @Override
    public ApiResponse getAllBranches(Pageable pageable) {
        Page<Branch> branchPage = branchRepository.findAll(pageable);
        ApiResponse.Pagination pagination = new ApiResponse.Pagination(
                branchPage.getNumber(),
                branchPage.getSize(),
                branchPage.getTotalElements(),
                branchPage.getTotalPages());
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Lấy danh sách chi nhánh thành công",
                branchPage.getContent(),
                pagination);
    }
}