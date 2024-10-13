package project.shop1.feature.product_refact.repository;

import project.shop1.entity.Book;

import java.util.Optional;

public interface ProductRepository {

    void saveProduct(Book book);

    Optional<Book> findBookByTitle(String title);


    }
