package com.example.mongodbn8napp.controller;

import com.example.mongodbn8napp.global.ApiResponse;
import com.example.mongodbn8napp.model.Branch.Branch;
import com.example.mongodbn8napp.service.branch.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/branches")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @PostMapping
    public ResponseEntity<ApiResponse<Branch>> createBranch(@Valid @RequestBody Branch branch) {
        Branch savedBranch = branchService.createBranch(branch);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(), "Tạo chi nhánh thành công", savedBranch, null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllBranches(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ApiResponse response = branchService.getAllBranches(pageable);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Branch>> getBranchById(@PathVariable("id") String id) {
        Branch branch = branchService.getBranchById(id).orElseThrow();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "Lấy chi nhánh thành công", branch, null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Branch>> updateBranch(
            @PathVariable("id") String id,
            @Valid @RequestBody Branch branch) {
        Branch updatedBranch = branchService.updateBranch(id, branch);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "Cập nhật chi nhánh thành công", updatedBranch, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBranch(@PathVariable("id") String id) {
        branchService.deleteBranch(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponse<>(HttpStatus.NO_CONTENT.value(), "Xóa chi nhánh thành công", null, null));
    }
}
