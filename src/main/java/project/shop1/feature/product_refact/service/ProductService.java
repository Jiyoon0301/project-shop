package project.shop1.feature.product_refact.service;

import project.shop1.feature.product_refact.dto.RequestDto.AddProductRequestDto;

public interface ProductService {

    void productRegistration(AddProductRequestDto addProductRequestDto);
}
