package com.example.mongodbn8napp.service.product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mongodbn8napp.model.Product;
import com.example.mongodbn8napp.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    @Override
    public Product createProduct(Product product) {
        // Thiết lập thời gian tạo và cập nhật khi tạo mới
        product.setDateAdd(LocalDateTime.now());
        product.setDateUpdate(LocalDateTime.now());
        
        // Đảm bảo các đối tượng nhúng không null trước khi truy cập
        if (product.getDescriptionInfo() != null) {
            product.getDescriptionInfo().setDateAdd(LocalDateTime.now());
            // Có thể bạn cũng muốn dateUpdate cho DescriptionInfo, nhưng thường không cần thiết
        }
        if (product.getAvailability() != null) {
            product.getAvailability().setDateAdd(LocalDateTime.now());
            // Tương tự cho Availability
        }
        
        // `images` và `price` sẽ được tự động đưa vào từ request body nếu có
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(String id, Product product) {
        Optional<Product> productData = productRepository.findById(id);

        if (productData.isPresent()) {
            Product _product = productData.get();
            _product.setSku(product.getSku());
            _product.setVisible(product.isVisible());

            // Cập nhật các trường nhúng (descriptionInfo, availability)
            if (product.getDescriptionInfo() != null) {
                _product.getDescriptionInfo().setName(product.getDescriptionInfo().getName());
                _product.getDescriptionInfo().setDescription(product.getDescriptionInfo().getDescription());
                // Không cần setDateAdd/Update cho descriptionInfo ở đây trừ khi bạn có logic riêng
            }
            if (product.getAvailability() != null) {
                _product.getAvailability().setQuality(product.getAvailability().getQuality());
                // Không cần setDateAdd/Update cho availability ở đây
            }
            
            // THÊM DÒNG NÀY ĐỂ CẬP NHẬT IMAGES
            // Có hai cách:
            // 1. Gán thẳng: Nếu bạn muốn thay thế toàn bộ danh sách ảnh
            _product.setImages(product.getImages()); 

            // 2. Hoặc nếu bạn muốn thêm/bóc tách logic cập nhật ảnh phức tạp hơn,
            //    ví dụ: chỉ thêm ảnh mới, xóa ảnh cũ theo ID, v.v.
            //    Tuy nhiên, cách gán thẳng là đơn giản nhất cho CRUD cơ bản.

            _product.setDateUpdate(LocalDateTime.now()); // Cập nhật thời gian cập nhật

            return productRepository.save(_product);
        }
        return null;
    }

    @Override
    public boolean deleteProduct(String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }
}