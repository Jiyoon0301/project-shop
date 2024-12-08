package project.shop1.domain.cart.service;

import project.shop1.domain.cart.dto.request.CartItemRequestDto;
import project.shop1.domain.cart.dto.response.CartItemResponseDto;

public interface CartService {

    // 장바구니에 상품 추가
    CartItemResponseDto addItemToCart(Long cartId, CartItemRequestDto request);
}
