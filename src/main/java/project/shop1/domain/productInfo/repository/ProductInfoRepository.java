package project.shop1.domain.productInfo.repository;

import project.shop1.entity.Book;

import java.util.Optional;

public interface ProductInfoRepository {

    Optional<Book> findBookByProductNumber(Long productNumber);



}