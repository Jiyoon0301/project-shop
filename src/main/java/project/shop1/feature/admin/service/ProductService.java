package project.shop1.feature.admin.service;

import project.shop1.entity.Book;
import project.shop1.entity.Author;
import project.shop1.feature.admin.dto.productDto.*;

import java.util.List;

public interface ProductService {

    /* 상품 등록 */
    void productRegistration(ProductRegistrationRequestDto productRegistrationRequestDto);

    /* 상품 등록 - 작가 검색 */
    Author searchAuthor(SearchAuthorRequestDto searchAuthorRequestDto);


    /* 상품 관리 페이지 */
    List<Book> adminSearchAllProduct(AdminSearchAllProductRequestDto adminSearchAllProductRequestDto);

    /* 상품 수정 */
    void updateProductInfo(UpdateProductInfoRequestDto updateProductInfoRequestDto);

    /* 상품 삭제 */
    void deleteProductByProductNumber(DeleteProductByProductNumberRequestDto deleteProductByProductNumberRequestDto);




    }