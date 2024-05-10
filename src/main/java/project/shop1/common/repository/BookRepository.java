package project.shop1.common.repository;

import project.shop1.entity.Book;

import java.util.Optional;

public interface BookRepository {

    /* bookId로 book 조회 */
    Optional<Book> findBookByBookId(Long bookId);


    /* productNumber로 book 조회 */
    Optional<Book> findBookByProductNumber(Long productNumber);

    }
