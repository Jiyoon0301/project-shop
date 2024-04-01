package project.shop1.feature.productInfo.service;

import project.shop1.entity.Book;
import project.shop1.feature.productInfo.dto.ProductInfoRequestDto;

import java.util.Optional;

public interface ProductInfoService {

    /* 상품 상세 페이지 */
    Optional<Book> productInfo(ProductInfoRequestDto productInfoRequestDto);




}
