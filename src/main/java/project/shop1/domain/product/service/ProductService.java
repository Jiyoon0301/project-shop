package project.shop1.domain.product.service;

import project.shop1.domain.product.dto.ResponseDto.ProductResponseDto;
import project.shop1.domain.product.dto.RequestDto.ProductRequestDto;

public interface ProductService {

    // 상품 생성
    ProductResponseDto createProduct(ProductRequestDto productRequest);

    // 상품 재고 추가
    ProductResponseDto addProduct(Long productId, int quantity);
}
