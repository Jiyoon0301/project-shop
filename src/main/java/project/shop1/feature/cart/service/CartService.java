package project.shop1.feature.cart.service;

import project.shop1.entity.CartItem;
import project.shop1.feature.cart.dto.DeleteCartRequestDto;
import project.shop1.feature.cart.dto.FindAllCartItemsByUserRequestDto;
import project.shop1.feature.cart.dto.UpdateProductQuantityRequestDto;

import java.util.List;

public interface CartService {

    /* 장바구니 목록 */
    List<CartItem> findAllCartItemsByUser(FindAllCartItemsByUserRequestDto findAllCartItemsByUserRequestDto);

    /* 장바구니에서 삭제 */
    int deleteCart(DeleteCartRequestDto deleteCartRequestDto);

    /* 장바구니 수량 수정 */
    int updateProductQuantity(UpdateProductQuantityRequestDto updateProductQuantityRequestDto);

    /* 장바구니 확인 */
    CartItem checkCart();
}
