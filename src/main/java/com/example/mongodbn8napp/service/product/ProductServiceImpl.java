package com.example.mongodbn8napp.service.product;

import com.example.mongodbn8napp.global.exception.NotFoundException;
import com.example.mongodbn8napp.global.ApiResponse;
import com.example.mongodbn8napp.global.exception.InvalidException;
import com.example.mongodbn8napp.model.Product;
import com.example.mongodbn8napp.model.ProductAvailability;
import com.example.mongodbn8napp.model.ProductDescription;
import com.example.mongodbn8napp.model.ProductImage;
import com.example.mongodbn8napp.model.Cate.Category;
import com.example.mongodbn8napp.repository.CategoryRepository;
import com.example.mongodbn8napp.repository.ProductRepository;
import com.example.mongodbn8napp.service.image.ImageUploadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ImageUploadService imageUploadService;

    @Override
    public Product createProduct(Product product, List<MultipartFile> files) {
        // Validation
        if (product.getSku() == null || product.getSku().isBlank()) {
            throw new InvalidException("SKU không được để trống");
        }
        // Kiểm tra SKU trùng lặp
        if (productRepository.existsBySku(product.getSku())) {
            throw new InvalidException("SKU '" + product.getSku() + "' đã tồn tại");
        }
        if (product.getDescriptionInfo() == null || product.getDescriptionInfo().getName() == null
                || product.getDescriptionInfo().getName().isBlank()) {
            throw new InvalidException("Tên sản phẩm không được để trống");
        }
        if (product.getAvailability() != null && product.getAvailability().getQuantity() < 0) {
            throw new InvalidException("Số lượng sản phẩm không được âm");
        }
        if (product.getPrice() == null || product.getPrice() <= 0) {
            throw new InvalidException("Giá sản phẩm phải lớn hơn 0");
        }
        if (product.getDiscountPrice() != null && product.getDiscountPrice() <= 0) {
            throw new InvalidException("Giá giảm không được nhỏ hơn hoặc bằng 0");
        }
        if (product.getDiscountPrice() != null && product.getDiscountPrice() >= product.getPrice()) {
            throw new InvalidException("Giá giảm phải nhỏ hơn giá gốc");
        }
        // Kiểm tra categoryId nếu có
        if (product.getCategoryId() != null && !product.getCategoryId().isBlank()) {
            Optional<Category> categoryOpt = categoryRepository.findById(product.getCategoryId());
            if (categoryOpt.isEmpty()) {
                throw new NotFoundException("Không tìm thấy danh mục với ID: " + product.getCategoryId());
            }
            product.setCategoryName(categoryOpt.get().getName());
        } else {
            product.setCategoryId(null);
            product.setCategoryName(null);
        }

        // Upload images to ImgBB if provided
        List<ProductImage> images = new ArrayList<>();
        if (files != null && !files.isEmpty()) {
            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                if (!file.isEmpty()) {
                    String imageUrl = imageUploadService.uploadImage(file);
                    ProductImage productImage = new ProductImage();
                    productImage.setUrl(imageUrl);
                    productImage.setAltText("Product image " + (i + 1));
                    productImage.setOrder(i);
                    productImage.setThumbnail(i == 0);
                    images.add(productImage);
                }
            }
        }
        product.setImages(images); // Có thể rỗng
        product.setDateAdd(LocalDateTime.now());
        product.setDateUpdate(LocalDateTime.now());
        if (product.getAvailability() != null) {
            product.getAvailability().setDateAdd(LocalDateTime.now());
            product.getAvailability().setDateUpdate(LocalDateTime.now());
        }
        if (product.getDescriptionInfo() != null) {
            product.getDescriptionInfo().setDateAdd(LocalDateTime.now());
            product.getDescriptionInfo().setDateUpdate(LocalDateTime.now());
        }
        return productRepository.save(product);
    }

    @Override
    public ApiResponse getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        ApiResponse.Pagination pagination = new ApiResponse.Pagination(
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages());
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Lấy danh sách sản phẩm thành công",
                productPage.getContent(),
                pagination);
    }

    @Override
    public Optional<Product> getProductById(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new NotFoundException("Không tìm thấy sản phẩm với ID: " + id);
        }
        return product;
    }

    @Override
    public Product updateProduct(String id, Product product) {
        Optional<Product> existingProductOpt = productRepository.findById(id);
        if (existingProductOpt.isEmpty()) {
            throw new NotFoundException("Không tìm thấy sản phẩm với ID: " + id);
        }

        Product existingProduct = existingProductOpt.get();

        // Validation chỉ áp dụng cho các trường được cung cấp
        if (product.getSku() != null) {
            if (product.getSku().isBlank()) {
                throw new InvalidException("SKU không được để trống");
            }
            existingProduct.setSku(product.getSku());
        }

        if (product.getVisible() != null) {
            existingProduct.setVisible(product.getVisible());
        }

        if (product.getCategoryId() != null) {
            if (product.getCategoryId().isBlank()) {
                throw new InvalidException("ID danh mục không được để trống");
            }
            Optional<Category> categoryOpt = categoryRepository.findById(product.getCategoryId());
            if (categoryOpt.isEmpty()) {
                throw new NotFoundException("Không tìm thấy danh mục với ID: " + product.getCategoryId());
            }
            existingProduct.setCategoryId(product.getCategoryId());
            existingProduct.setCategoryName(categoryOpt.get().getName());
        }
        if (product.getPrice() != null) {
            if (product.getPrice() <= 0) {
                throw new InvalidException("Giá sản phẩm phải lớn hơn 0");
            }
            existingProduct.setPrice(product.getPrice());
        }
        if (product.getDiscountPrice() != null) {
            if (product.getDiscountPrice() <= 0) {
                throw new InvalidException("Giá giảm không được nhỏ hơn hoặc bằng 0");
            }
            if (product.getPrice() != null && product.getDiscountPrice() >= product.getPrice()) {
                throw new InvalidException("Giá giảm phải nhỏ hơn giá gốc");
            } else if (product.getPrice() == null && product.getDiscountPrice() >= existingProduct.getPrice()) {
                throw new InvalidException("Giá giảm phải nhỏ hơn giá gốc");
            }
            existingProduct.setDiscountPrice(product.getDiscountPrice());
        } else if (product.getPrice() != null) {
            // Nếu cập nhật giá gốc mà không cung cấp giá giảm, xóa giá giảm
            existingProduct.setDiscountPrice(null);
        }

        if (product.getDescriptionInfo() != null) {
            ProductDescription description = existingProduct.getDescriptionInfo() != null
                    ? existingProduct.getDescriptionInfo()
                    : new ProductDescription();

            if (product.getDescriptionInfo().getName() != null) {
                if (product.getDescriptionInfo().getName().isBlank()) {
                    throw new InvalidException("Tên sản phẩm không được để trống");
                }
                description.setName(product.getDescriptionInfo().getName());
            }

            if (product.getDescriptionInfo().getDescription() != null) {
                description.setDescription(product.getDescriptionInfo().getDescription());
            }

            existingProduct.setDescriptionInfo(description);
        }

        if (product.getAvailability() != null && product.getAvailability().getQuantity() != null) {
            if (product.getAvailability().getQuantity() < 0) {
                throw new InvalidException("Số lượng sản phẩm không được âm");
            }
            ProductAvailability availability = existingProduct.getAvailability() != null
                    ? existingProduct.getAvailability()
                    : new ProductAvailability();
            availability.setQuantity(product.getAvailability().getQuantity());
            existingProduct.setAvailability(availability);
        }

        if (product.getImages() != null) {
            existingProduct.setImages(product.getImages());
        }

        existingProduct.setDateUpdate(LocalDateTime.now());
        if (existingProduct.getAvailability() != null) {
            existingProduct.getAvailability().setDateUpdate(LocalDateTime.now());
        }
        if (existingProduct.getDescriptionInfo() != null) {
            existingProduct.getDescriptionInfo().setDateUpdate(LocalDateTime.now());
        }

        return productRepository.save(existingProduct);
    }

    @Override
    public boolean deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Không tìm thấy sản phẩm với ID: " + id);
        }
        productRepository.deleteById(id);
        return true;
    }

    @Override
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }
}