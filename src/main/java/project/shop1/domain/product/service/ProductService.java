package project.shop1.domain.product.service;

import project.shop1.domain.product.dto.RequestDto.AddProductRequestDto;
import project.shop1.domain.product.dto.ResponseDto.SearchProductsResponseDto;

public interface ProductService {

    Long addProduct(AddProductRequestDto addProductRequestDto);

    SearchProductsResponseDto findProductsByKeyword(String title);

    SearchProductsResponseDto findProductById(Long id);
}
