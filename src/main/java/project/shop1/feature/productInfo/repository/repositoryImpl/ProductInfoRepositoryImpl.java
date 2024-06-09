package project.shop1.feature.productInfo.repository.repositoryImpl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.shop1.entity.Book;
import project.shop1.feature.productInfo.repository.ProductInfoRepository;

import java.util.Optional;

import static project.shop1.entity.QBook.book;

@Repository
public class ProductInfoRepositoryImpl implements ProductInfoRepository {
    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public ProductInfoRepositoryImpl(EntityManager entityManager){
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(entityManager);
    }

    @Override
    public Optional<Book> findBookByProductNumber(Long productNumber){
        Book result = jpaQueryFactory
                .selectFrom(book)
                .where(book.productNumber.eq(productNumber))
                .fetchOne();

        return Optional.ofNullable(result);
    }


}
