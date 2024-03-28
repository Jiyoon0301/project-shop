package project.shop1.feature.cart.repository;

import project.shop1.entity.Book;
import project.shop1.entity.CartItem;
import project.shop1.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface CartRepository {

    /* 장바구니 담기 버튼 */
    void addCart(CartItem cartItem);

    /* 장바구니 목록 */
    List<CartItem> findAllCartItemsByUser(UserEntity userEntity);

    /* 장바구니에서 삭제 */
    void deleteCart(CartItem cartItem);

    /* 장바구니 수량 수정 */
    void increaseQuantity(Long cartItemId);
    void decreaseQuantity(Long cartItemId);

    /* 장바구니 확인 */
    CartItem checkCart();

    /* 장바구니 조회 */
    Optional<CartItem> findCartItemById(Long cartItemId);
    Optional<Book> findBookByProductNumber(Long productNumber);
    Optional<CartItem> findCartItemByProductNumberAndUserAccount(String account, Long productNumber);

    /* 장바구니 상품 수량 확인 */
    int checkQuantity(Long cartItemId);



    }
