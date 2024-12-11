package project.shop1.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shop1.domain.product.entity.Book;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainingIgnoreCase(String keyword);

    Optional<Book> findById(Long id);

    List<Book> findAll();
}