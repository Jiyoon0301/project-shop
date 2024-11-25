package project.shop1.domain.product.productInfo_needRefactor.repository;

import project.shop1.domain.product.entity.Book;

import java.util.Optional;

public interface ProductInfoRepository {

    Optional<Book> findBookByProductNumber(Long productNumber);



}
