package project.shop1.domain.product.productSearch_needRefactor.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.shop1.domain.product.entity.Book;
import project.shop1.domain.product.productSearch_needRefactor.repository.ProductSearchRepository;

import java.util.List;
import java.util.Optional;

import static project.shop1.domain.product.entity.QBook.book;

@Repository
public class ProductSearchRepositoryImpl implements ProductSearchRepository {

    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public ProductSearchRepositoryImpl(EntityManager entityManager){
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(entityManager);
    }

    /* 상품 검색 - 검색어, 카테고리 */
    @Override // keyword, category == null, criteria = title or authorName
    public Optional<List<Book>> findByKeywordAndCategory(String criteria, String keyword, String category, int amount, int pageNum){
        BooleanBuilder builder = new BooleanBuilder();

        /* criteria가 "title" 또는 "authorName"일 때 */
        if (keyword != null){
            if ("title".equals(criteria)) {
                builder.and(book.title.contains(keyword));
            } else if ("authorName".equals(criteria)) {
                builder.and(book.authorName.contains(keyword));
            }
        }

        /* category가 주어진 경우 */
        if (category != null) {
            if (builder != null) {
                builder.and(book.category.eq(category));
            }
        }

        List<Book> result = jpaQueryFactory
                .selectFrom(book)
                .where(builder)
                .offset((pageNum - 1) * amount)
                .limit(amount)
                .fetch();

        return Optional.ofNullable(result);
    }

    /* 인기순위(판매순) 상품 조회 */
    @Override
    public Optional<List<Book>> findBookByRanking(int amount, int pageNum){
        List<Book> result = jpaQueryFactory
                .selectFrom(book)
                .orderBy(book.sold.desc())
                .offset((pageNum-1)*amount)
                .limit(amount)
                .fetch();

        return Optional.ofNullable(result);
    }
}
