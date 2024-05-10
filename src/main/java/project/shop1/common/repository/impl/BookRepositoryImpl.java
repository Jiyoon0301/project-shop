package project.shop1.common.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.shop1.common.repository.BookRepository;
import project.shop1.entity.Book;

import java.util.Optional;

import static project.shop1.entity.QBook.book;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public BookRepositoryImpl(EntityManager entityManager){
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(entityManager);
    }

    /* id로 book 조회 */
    @Override
    public Optional<Book> findBookByBookId(Long bookId){
        Book findBook = jpaQueryFactory
                .selectFrom(book)
                .where(book.id.eq(bookId))
                .fetchOne();

        return Optional.ofNullable(findBook);
    }

    /* productNumber로 book 조회 */
    @Override
    public Optional<Book> findBookByProductNumber(Long productNumber){
        Book findBook = jpaQueryFactory
                .selectFrom(book)
                .where(book.productNumber.eq(productNumber))
                .fetchOne();

        return Optional.ofNullable(findBook);
    }
}
