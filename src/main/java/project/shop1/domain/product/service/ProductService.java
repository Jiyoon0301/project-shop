package project.shop1.domain.product.service;

import project.shop1.domain.product.dto.RequestDto.ProductUpdateRequestDto;
import project.shop1.domain.product.dto.ResponseDto.ProductResponseDto;
import project.shop1.domain.product.dto.RequestDto.ProductRequestDto;

import java.util.List;

public interface ProductService {

    // 상품 생성
    ProductResponseDto createProduct(ProductRequestDto productRequest);

    // 상품 재고 추가
    ProductResponseDto addProduct(Long productId, int quantity);

    // id로 상품 조회
    ProductResponseDto getProductById(Long productId);

    // 모든 상품 조회
    List<ProductResponseDto> getAllProducts();

    // 상품 검색
    List<ProductResponseDto> searchProducts(String keyword);

    // 상품 업데이트
    ProductResponseDto updateProduct(Long productId, ProductUpdateRequestDto updateRequestDto);

    // 상품 삭제
    void deleteProduct(Long productId);

    // 상품 재고 업데이트
    ProductResponseDto updateStock(Long productId, int quantity);
}
