    package com.example.mongodbn8napp.controller;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import com.example.mongodbn8napp.model.Product;
    import com.example.mongodbn8napp.service.product.ProductService;

    import java.util.List;
    import java.util.Optional;

    @RestController
    @RequestMapping("/api/products")
    @CrossOrigin(origins = "*") // Cho phép mọi domain truy cập API (CHỈ DÙNG CHO DEMO/PHÁT TRIỂN!)
    public class ProductController {

        @Autowired // Inject ProductService
        private ProductService productService;

        // --- 1. Thêm sản phẩm mới (Create) ---
        @PostMapping
        public ResponseEntity<Product> createProduct(@RequestBody Product product) {
            try {
                Product _product = productService.createProduct(product);
                return new ResponseEntity<>(_product, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // --- 2. Lấy tất cả sản phẩm (Read All) ---
        @GetMapping
        public ResponseEntity<List<Product>> getAllProducts() {
            try {
                List<Product> products = productService.getAllProducts();
                if (products.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(products, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // --- 3. Lấy sản phẩm theo ID (Read One) ---
        @GetMapping("/{id}")
        public ResponseEntity<Product> getProductById(@PathVariable("id") String id) {
            Optional<Product> productData = productService.getProductById(id);

            return productData.map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        // --- 4. Cập nhật sản phẩm (Update) ---
        @PutMapping("/{id}")
        public ResponseEntity<Product> updateProduct(@PathVariable("id") String id, @RequestBody Product product) {
            Product updatedProduct = productService.updateProduct(id, product);
            if (updatedProduct != null) {
                return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        // --- 5. Xóa sản phẩm (Delete) ---
        @DeleteMapping("/{id}")
        public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") String id) {
            try {
                boolean deleted = productService.deleteProduct(id);
                if (deleted) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Không tìm thấy sản phẩm để xóa
                }
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // --- 6. Xóa tất cả sản phẩm (Delete All) ---
        @DeleteMapping
        public ResponseEntity<HttpStatus> deleteAllProducts() {
            try {
                productService.deleteAllProducts();
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }