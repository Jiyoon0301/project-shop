package project.shop1.feature.order.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.shop1.entity.Book;
import project.shop1.entity.Order;
import project.shop1.entity.QBook;
import project.shop1.entity.UserEntity;
import project.shop1.feature.order.repository.OrderRepository;
import static project.shop1.entity.QUserEntity.userEntity;
import static project.shop1.entity.QBook.book;

import java.util.List;
import java.util.Optional;


@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public OrderRepositoryImpl(EntityManager entityManager){
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(entityManager);
    }
//    public Order findOne(Long id){
//        return entityManager.find(Order.class,id);
//    }
//
//    public Book findItemByName(String itemName) {
//        return null;
//    }

    /* 주소 저장 */
    @Override
    public void saveAddress(String account, String roadAddress, String detailedAddress) {
        jpaQueryFactory
                .update(userEntity)
                .where(userEntity.account.eq(account))
                .set(userEntity.address, roadAddress + " " + detailedAddress)
                .execute();
    }

    /* bookId로 상품 찾기 */
    @Override
    public Optional<Book> findBookbyBookId(Long bookId) {
        Book findbook = jpaQueryFactory
                .selectFrom(book)
                .where(book.id.eq(bookId))
                .fetchOne();
        return Optional.ofNullable(findbook);
    }


//    public List<Order> findAll(OrderSearch orderSearch){}
}
