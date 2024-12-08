package project.shop1.domain.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.shop1.domain.product.dto.ResponseDto.ProductResponseDto;
import project.shop1.domain.product.dto.RequestDto.ProductRequestDto;
import project.shop1.domain.product.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("products/")
public class ProductController {

    private final ProductService productService;

    /**
     * 새로운 상품 생성
     *
     * @param productRequest
     * @return
     */
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody @Valid ProductRequestDto productRequest) {
        ProductResponseDto createdProduct = productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    /**
     * 존재하는 상품 재고 추가
     *
     * @param productId
     * @param quantity
     * @return
     */
    @PutMapping("/{productId}/add")
    public ResponseEntity<ProductResponseDto> addProduct(
            @PathVariable Long productId,
            @RequestParam int quantity) {
        ProductResponseDto updatedProduct = productService.addProduct(productId, quantity);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * productId로 상품 조회
     *
     * @param productId
     * @return
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long productId) {
        ProductResponseDto product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    /**
     * 모든 상품 조회
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * 상품 검색
     * @param keyword
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDto>> searchProducts(@RequestParam String keyword) {
        List<ProductResponseDto> products = productService.searchProducts(keyword);
        return ResponseEntity.ok(products);
    }
}
