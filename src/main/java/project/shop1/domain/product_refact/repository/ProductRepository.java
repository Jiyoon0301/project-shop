package project.shop1.domain.product_refact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shop1.domain.product_refact.entity.Book;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Book, Long> {

    void saveProduct(Book book);

    Optional<Book> findByTitle(String title);

    Optional<Book> findById(Long id);

    List<Book> findAll();
}
