package project.shop1.domain.cart.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.shop1.domain.cart.entity.CartItem;
import project.shop1.domain.product.entity.Book;
import project.shop1.domain.user.entity.UserEntity;
import project.shop1.domain.cart.repository.CartRepository;

import static project.shop1.entity.QBook.book;
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
    public List<CartItem> findAllCartItemsByUser(UserEntity userEntity) {
        List<CartItem> result = jpaQueryFactory
                .selectFrom(cartItem)
                .where(cartItem.userEntity.eq(userEntity))
                .fetch();
        return result;
    }

    /* 장바구니 체크 상품 총 가격 */
    @Override
    public int totalPrice(List<Long> cartItemsId) {
        int totalPrice = 0;
        for (int i =0 ; i<cartItemsId.size();i++){
            com.querydsl.core.Tuple result = (Tuple) jpaQueryFactory
                    .select(cartItem.book.price, cartItem.quantity)
                    .from(cartItem)
                    .where(cartItem.id.eq(cartItemsId.get(0)))
                    .fetchOne();
            Integer price = result.get(0, Integer.class);
            Integer quantity = result.get(1, Integer.class);
            totalPrice+=price*quantity;
        }
        return totalPrice;
    }

    /* 장바구니 담기 버튼 */
    public void addCart(CartItem cartItem){
        entityManager.persist(cartItem);
    }

    /* 장바구니에서 삭제 */
    @Override
    public void deleteCart(CartItem cartItem) {
        entityManager.remove(cartItem);
    }

    /* 장바구니 수량 수정 */
    @Override
    public void increaseQuantity(Long cartItemId) {
        Long count = jpaQueryFactory
                .update(cartItem)
                .set(cartItem.quantity, cartItem.quantity.add(1))
                .execute();
    }
    @Override
    public void decreaseQuantity(Long cartItemId) {
        Long count = jpaQueryFactory
                .update(cartItem)
                .set(cartItem.quantity, cartItem.quantity.subtract(1))
                .execute();
    }

    /* 장바구니 확인 */
    @Override
    public CartItem checkCart() {
        return null;
    }

    /* 장바구니 조회 */
    @Override
    public Optional<CartItem> findCartItemById(Long cartItemId){
        CartItem result = jpaQueryFactory
                .selectFrom(cartItem)
                .where(cartItem.id.eq(cartItemId))
                .fetchOne();
        return Optional.ofNullable(result);
    }
    @Override
    public Optional<Book> findBookByProductNumber(Long productNumber){
        Book result = jpaQueryFactory
                .selectFrom(book)
                .where(book.productNumber.eq(productNumber))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<CartItem> findCartItemByProductNumberAndUserAccount(String account, Long productNumber){
        CartItem result = jpaQueryFactory
                .selectFrom(cartItem)
                .where(cartItem.userEntity.account.eq(account))
                .where(cartItem.book.productNumber.eq(productNumber))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    /* 장바구니에 상품 수량 확인 */
    @Override
    public int checkQuantity(Long cartItemId){
        Integer result = jpaQueryFactory
                .select(cartItem.quantity)
                .from(cartItem)
                .where(cartItem.id.eq(cartItemId))
                .fetchOne();
        return result;
    }


}
