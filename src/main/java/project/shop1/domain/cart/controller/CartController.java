package project.shop1.domain.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.shop1.domain.cart.dto.request.CartItemRequestDto;
import project.shop1.domain.cart.dto.response.CartItemResponseDto;
import project.shop1.domain.cart.service.CartService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    // 1. Add Item to Cart

    /**
     * 장바구니에 상품 추가
     * @param cartId
     * @param request
     * @return
     */
    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemResponseDto> addItemToCart(@PathVariable Long cartId, @RequestBody CartItemRequestDto request) {
        CartItemResponseDto response = cartService.addItemToCart(cartId, request);
        return ResponseEntity.ok(response);
    }
}
