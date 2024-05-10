package project.shop1.feature.admin.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.shop1.common.exception.BusinessException;
import project.shop1.common.exception.ErrorCode;
import project.shop1.entity.Author;
import project.shop1.entity.Book;
import project.shop1.entity.QBook;
import project.shop1.feature.admin.dto.productDto.UpdateProductInfoRequestDto;
import project.shop1.feature.admin.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

import static project.shop1.entity.QBook.book;


@Repository
public class ProductRepositoryImpl implements ProductRepository { //데이터베이스에 접근, 도메인 객체를 DB에 저장하고 관리

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

    /* 상품 조회 */
    @Override
    public Optional<List<Book>> findAll(int pageNumber){
        List<Book> result = jpaQueryFactory
                .selectFrom(book)
                .orderBy(book.productNumber.asc())
                .offset((pageNumber-1)*10)
                .limit(10)
                .fetch();
        return Optional.ofNullable(result);

    }
    @Override
    public Optional<Book> findBookByTitle(String title){
        Book findBook = jpaQueryFactory
                .selectFrom(book)
                .where(book.title.eq(title))
                .fetchOne();
        return Optional.ofNullable(findBook);
    }
    @Override
    public Optional<Book> findBookByProductNumber(Long productNumber){
        Book findBook = jpaQueryFactory
                .selectFrom(QBook.book)
                .where(QBook.book.productNumber.eq(productNumber))
                .fetchOne();
        return Optional.ofNullable(findBook);
    }


    /* 상품 수정 */
    @Override
    public void updateProductInfo(UpdateProductInfoRequestDto updateProductInfoRequestDto, Author author){
        Long productNumber = updateProductInfoRequestDto.getProductNumber();
        String title = updateProductInfoRequestDto.getTitle();
        int price = updateProductInfoRequestDto.getPrice();
        int stockQuantity = updateProductInfoRequestDto.getStockQuantity();
        String category = updateProductInfoRequestDto.getCategory();

        long count = jpaQueryFactory
                .update(book)
                .set(book.title,title)
                .set(book.author,author)
                .set(book.authorName,author.getAuthorName())
                .set(book.price,price)
                .set(book.stockQuantity, stockQuantity)
                .set(book.category,category)
                .where(book.productNumber.eq(productNumber))
                .execute();
        if (count == 0){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "상품이 없습니다.");
        }
        Book book = findBookByProductNumber(productNumber).get();
        book.setTitle(title);
        book.setAuthor(author);
        book.setAuthorName(author.getAuthorName());
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        book.setCategory(category);
    }

    /* 상품 삭제 */
    @Override
    public void deleteProduct(Long productNumber){
        Book bookToDelete = findBookByProductNumber(productNumber).get();
        entityManager.remove(bookToDelete);
    }

    /* 상품 번호 */
    @Override
    public Long productNumber(){
        Long count = jpaQueryFactory
                .select(book.count())
                .from(book)
                .fetchOne();
        count = (count != null) ? count : 0L;
        return count;
    }



}
