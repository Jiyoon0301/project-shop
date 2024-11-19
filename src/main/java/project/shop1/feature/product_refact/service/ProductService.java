package project.shop1.feature.product_refact.service;

import project.shop1.entity.Book;
import project.shop1.feature.product_refact.dto.RequestDto.AddProductRequestDto;
import project.shop1.feature.product_refact.dto.ResponseDto.SearchProductsResponseDto;

import java.util.List;

public interface ProductService {

    Long addProduct(AddProductRequestDto addProductRequestDto);

    SearchProductsResponseDto findProductsByKeyword(String title);

    SearchProductsResponseDto findProductById(Long id);
}
