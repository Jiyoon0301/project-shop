package project.shop1.feature.cart.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.shop1.entity.CartItem;
import project.shop1.entity.QCartItem;
import project.shop1.entity.QUserEntity;
import project.shop1.entity.UserEntity;
import project.shop1.feature.cart.repository.CartRepository;
import static project.shop1.entity.QUserEntity.userEntity;
import static project.shop1.entity.QCartItem.cartItem;

import java.util.List;
import java.util.Optional;

@Repository
public class CartRepositoryImpl implements CartRepository {
    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public CartRepositoryImpl(EntityManager entityManager){
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(entityManager);
    }

    /* 장바구니 목록 */
    @Override
    public List<CartItem> findAllCartItemsByUser(String account) {
        List<CartItem> items = jpaQueryFactory
                .selectFrom(QCartItem.cartItem)
                .join(cartItem.userEntity, userEntity)
                .where(userEntity.account.eq(account))
                .fetch();
        return items;
    }

    /* 장바구니에서 삭제 */
    @Override
    public int deleteCart(int cartId) {
        return 0;
    }

    /* 장바구니 수량 수정 */
    @Override
    public int updateProductQuantity() {
        return 0;
    }

    /* 장바구니 확인 */
    @Override
    public CartItem checkCart() {
        return null;
    }

//    @Override
//    public Optional<Book> findBookByProductNumber(Long productNumber){
//        Book result = jpaQueryFactory
//                .selectFrom(book)
//                .where(book.productNumber.eq(productNumber))
//                .fetchOne();
//
//        return Optional.ofNullable(result);
//    }
}
