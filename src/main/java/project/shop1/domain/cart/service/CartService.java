package project.shop1.domain.cart.service;

import project.shop1.domain.cart.dto.request.AddProductRequestDto;
import project.shop1.domain.cart.dto.request.CartItemRequestDto;
import project.shop1.domain.cart.dto.request.CartItemUpdateRequestDto;
import project.shop1.domain.cart.dto.response.CartItemResponseDto;
import project.shop1.domain.cart.dto.response.CartResponseDto;

import java.util.List;

public interface CartService {

    // 장바구니에 상품 추가
    CartItemResponseDto addItemToCart(Long cartId, CartItemRequestDto request);

    // 장바구니에 새로운 상품 추가
    CartResponseDto addProductToCart(Long userId, AddProductRequestDto request);

    // 장바구니 상품 조회
    List<CartItemResponseDto> getCartItems(Long cartId);

    // 사용자 장바구니 조회
    CartResponseDto getCartByUserId(Long userId);

    // 장바구니 상품 업데이트
    CartItemResponseDto updateCartItem(Long cartId, Long itemId, CartItemUpdateRequestDto request);

    // 장바구니 상품 수량 업데이트
    CartItemResponseDto updateQuantity(Long cartId, Long itemId, int quantity);
}
