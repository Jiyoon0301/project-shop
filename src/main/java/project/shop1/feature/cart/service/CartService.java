package project.shop1.feature.cart.service;

import project.shop1.entity.CartItem;
import project.shop1.feature.cart.dto.*;

import java.util.List;

public interface CartService {

    /* 장바구니 목록 */
    List<CartItem> findAllCartItemsByUser(FindAllCartItemsByUserRequestDto findAllCartItemsByUserRequestDto);

    /* 장바구니 체크 상품 총 가격 */
    int totalPrice(TotalPriceRequestDto totalPriceRequestDto);


    /* 장바구니 담기 버튼 */
    void addCart(AddCartRequestDto addCartRequestDto);

        /* 장바구니에서 삭제 */
    void deleteCart(DeleteCartRequestDto deleteCartRequestDto);

    /* 장바구니 수량 수정 */
    void updateProductQuantity(UpdateQuantityByOneRequestDto updateQuantityByOneRequestDto);

    /* 장바구니 확인 */
    CartItem checkCart();
}
