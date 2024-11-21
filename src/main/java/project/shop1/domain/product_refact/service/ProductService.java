package project.shop1.domain.product_refact.service;

import project.shop1.domain.product_refact.dto.RequestDto.AddProductRequestDto;
import project.shop1.domain.product_refact.dto.ResponseDto.SearchProductsResponseDto;

public interface ProductService {

    Long addProduct(AddProductRequestDto addProductRequestDto);

    SearchProductsResponseDto findProductsByKeyword(String title);

    SearchProductsResponseDto findProductById(Long id);
}
