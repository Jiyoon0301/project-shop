package project.shop1.feature.productInfo.service;

import project.shop1.entity.Book;
import project.shop1.feature.productInfo.dto.AddCartRequestDto;
import project.shop1.feature.productInfo.dto.ProductInfoRequestDto;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ProductInfoService {

    /* 상품 상세 페이지 */
    Optional<Book> productInfo(ProductInfoRequestDto productInfoRequestDto);




}
