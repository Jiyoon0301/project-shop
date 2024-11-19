package project.shop1.feature.product_refact.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import project.shop1.entity.Book;
import project.shop1.feature.product_refact.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

import static project.shop1.entity.QBook.book;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @PersistenceContext
    private EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;
    public ProductRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    /* 상품 저장*/
    public void saveProduct(Book book) {
        entityManager.persist(book);
    }

    /* 상품 검색 */
    @Override
    public Optional<Book> findByTitle(String title) {
        Book result = jpaQueryFactory
                .selectFrom(book)
                .where(book.title.eq(title)).fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book result = jpaQueryFactory
                .selectFrom(book)
                .where(book.id.eq(id))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public List<Book> findAll() {
        return jpaQueryFactory
                .selectFrom(book)
                .fetch();
    }
}
