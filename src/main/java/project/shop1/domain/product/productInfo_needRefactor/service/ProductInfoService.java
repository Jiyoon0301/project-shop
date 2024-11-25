package project.shop1.domain.product.productInfo_needRefactor.service;

import project.shop1.domain.product.entity.Book;
import project.shop1.domain.product.productInfo_needRefactor.dto.ProductInfoRequestDto;

import java.util.Optional;

public interface ProductInfoService {

    /* 상품 상세 페이지 */
    Optional<Book> productInfo(ProductInfoRequestDto productInfoRequestDto);




}
