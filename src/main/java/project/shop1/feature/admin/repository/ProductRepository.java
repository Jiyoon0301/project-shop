package project.shop1.feature.admin.repository;

import project.shop1.entity.Author;
import project.shop1.entity.Book;
import project.shop1.feature.admin.dto.productDto.UpdateProductInfoRequestDto;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    /* 상품 저장 */
    void saveProduct(Book book);

    /* 상품 조회 */
    Optional<List<Book>> findAll(int pageNumber);

    Optional<Book> findBookByTitle(String title);

    Optional<Book> findBookByProductNumber(Long productNumber);


        /* 상품 수정 */
    void updateProductInfo(UpdateProductInfoRequestDto updateProductInfoRequestDto, Author author);

    /* 상품 삭제 */
    void deleteProduct(Long productNumber);


        /* 상품 번호 */
    Long setproductNumber();



    }