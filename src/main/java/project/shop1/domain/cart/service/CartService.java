package project.shop1.domain.cart.service;

import project.shop1.domain.cart.dto.request.AddProductRequestDto;
import project.shop1.domain.cart.dto.request.CartItemRequestDto;
import project.shop1.domain.cart.dto.response.CartItemResponseDto;
import project.shop1.domain.cart.dto.response.CartResponseDto;

public interface CartService {

    // 장바구니에 상품 추가
    CartItemResponseDto addItemToCart(Long cartId, CartItemRequestDto request);

    // 장바구니에 새로운 상품 추가
    CartResponseDto addProductToCart(Long userId, AddProductRequestDto request);
}
