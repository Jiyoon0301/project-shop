package project.shop1.feature.productInfo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.common.validation.ValidationSequence;
import project.shop1.entity.Book;
import project.shop1.feature.productInfo.dto.ProductInfoRequestDto;
import project.shop1.feature.productInfo.service.ProductInfoService;
import project.shop1.feature.productSearch.dto.findByKeywordAndCategoryRequestDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductInfoController {

    private final ProductInfoService productInfoService;

    /* 상품 검색 - 검색어, 카테고리 */
    @PostMapping("/productInfo") //productInfoRequestDto : Long productNumber
    public List<Book> productInfo(@Validated(value = ValidationSequence.class) @RequestBody ProductInfoRequestDto productInfoRequestDto){
        Book result = productInfoService.findByKeywordAndCategory(findByKeywordAndCategoryRequestDto);
        return result;
    }
}
