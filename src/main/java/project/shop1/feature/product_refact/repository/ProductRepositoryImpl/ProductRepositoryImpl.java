package project.shop1.feature.product_refact.repository.ProductRepositoryImpl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.shop1.entity.Book;
import project.shop1.feature.product_refact.repository.ProductRepository;

import java.util.Optional;

import static project.shop1.entity.QBook.book;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public ProductRepositoryImpl(EntityManager entityManager){
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(entityManager);
    }

    /* 상품 저장*/
    public void saveProduct(Book book) {
        entityManager.persist(book);
    }

    /* 상품 검색 */
    public Optional<Book> findBookByTitle(String title){
        Book result =  jpaQueryFactory
                .selectFrom(book)
                .where(book.title.eq(title)).fetchOne();
        return Optional.ofNullable(result);
    }
}
