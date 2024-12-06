//package project.shop1.domain.product.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import project.shop1.domain.product.dto.RequestDto.AddProductRequestDto;
//import project.shop1.domain.product.service.ProductService;
//import project.shop1.global.util.reponse.BooleanResponse;
//import project.shop1.global.util.validation.ValidationSequence;
//import project.shop1.domain.product.entity.Book;
//import project.shop1.domain.product.dto.ResponseDto.SearchProductsResponseDto;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("products/")
//public class ProductController {
//
//    private final ProductService productService;
//
//    // 1. Create a new product
//    @PostMapping
//    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductRequestDto productRequest) {
//        ProductDto createdProduct = productService.createProduct(productRequest);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
//    }
//
//    // 2. Add an existing product to the catalog (optional based on use case)
//    @PutMapping("/{productId}/add")
//    public ResponseEntity<ProductDto> addProduct(
//            @PathVariable Long productId,
//            @RequestParam int quantity) {
//        ProductDto updatedProduct = productService.addProduct(productId, quantity);
//        return ResponseEntity.ok(updatedProduct);
//    }
//
//    // 3. Get product by ID
//    @GetMapping("/{productId}")
//    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId) {
//        ProductDto product = productService.getProductById(productId);
//        return ResponseEntity.ok(product);
//    }
//
//    // 4. Get all products
//    @GetMapping
//    public ResponseEntity<List<ProductDto>> getAllProducts() {
//        List<ProductDto> products = productService.getAllProducts();
//        return ResponseEntity.ok(products);
//    }
//
//    // 5. Search products
//    @GetMapping("/search")
//    public ResponseEntity<List<ProductDto>> searchProducts(@RequestParam String keyword) {
//        List<ProductDto> products = productService.searchProducts(keyword);
//        return ResponseEntity.ok(products);
//    }
//
//    // 6. Update a product
//    @PutMapping("/{productId}")
//    public ResponseEntity<ProductDto> updateProduct(
//            @PathVariable Long productId,
//            @RequestBody @Valid ProductUpdateDto updateRequest) {
//        ProductDto updatedProduct = productService.updateProduct(productId, updateRequest);
//        return ResponseEntity.ok(updatedProduct);
//    }
//
//    // 7. Delete a product
//    @DeleteMapping("/{productId}")
//    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
//        productService.deleteProduct(productId);
//        return ResponseEntity.noContent().build();
//    }
//
//    // 8. Update stock for a product
//    @PutMapping("/{productId}/stock")
//    public ResponseEntity<ProductDto> updateStock(
//            @PathVariable Long productId,
//            @RequestParam int quantity) {
//        ProductDto updatedProduct = productService.updateStock(productId, quantity);
//        return ResponseEntity.ok(updatedProduct);
//    }
//
//    // 9. Get stock level for a product
//    @GetMapping("/{productId}/stock")
//    public ResponseEntity<Integer> getStockLevel(@PathVariable Long productId) {
//        int stockLevel = productService.getStockLevel(productId);
//        return ResponseEntity.ok(stockLevel);
//    }
//}
