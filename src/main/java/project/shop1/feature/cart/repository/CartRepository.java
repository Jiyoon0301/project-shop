package project.shop1.feature.cart.repository;

import project.shop1.entity.CartItem;

import java.util.List;

public interface CartRepository {

    /* 장바구니 목록 */
    List<CartItem> findAllCartItemsByUser(String account);

    /* 장바구니에서 삭제 */
    int deleteCart(int cartId);

    /* 장바구니 수량 수정 */
    int updateProductQuantity();

    /* 장바구니 확인 */
    CartItem checkCart();

}
