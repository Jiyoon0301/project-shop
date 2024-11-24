package project.shop1.domain.main.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.shop1.domain.product_refact.entity.Book;
import project.shop1.domain.main.repository.MainRepository;

import java.util.List;

import static project.shop1.entity.QBook.book;

@Repository
public class MainRepositoryImpl implements MainRepository {
    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public MainRepositoryImpl(EntityManager entityManager){
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(entityManager);
    }

    /* 상품 조회 - 평점 높은 순서 */
    @Override
    public List<Book> findProductsByRanking(int page){
        List<Book> findBook = jpaQueryFactory
                .selectFrom(book)
                .orderBy(book.sold.desc())
                .offset(4*(page-1))
                .limit(4)
                .fetch();
        return findBook;
    }

}
