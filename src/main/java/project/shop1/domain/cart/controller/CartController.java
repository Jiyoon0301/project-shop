package project.shop1.domain.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.shop1.domain.cart.dto.request.AddProductRequestDto;
import project.shop1.domain.cart.dto.request.CartItemRequestDto;
import project.shop1.domain.cart.dto.response.CartItemResponseDto;
import project.shop1.domain.cart.dto.response.CartResponseDto;
import project.shop1.domain.cart.service.CartService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    /**
     * 장바구니에 상품 추가
     * @param cartId
     * @param request
     * @return
     */
    @PostMapping("/{cartId}/items")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CartItemResponseDto> addItemToCart(@PathVariable Long cartId, @RequestBody CartItemRequestDto request) {
        CartItemResponseDto response = cartService.addItemToCart(cartId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * 장바구니에 새로운 상품 추가
     * @param userId
     * @param request
     * @return
     */
    @PostMapping("/{userId}/products")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CartResponseDto> addProductToCart(@PathVariable Long userId, @RequestBody AddProductRequestDto request) {
        CartResponseDto response = cartService.addProductToCart(userId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * 장바구니 상품 조회
     * @param cartId
     * @return
     */
    @GetMapping("/{cartId}/items")
    public ResponseEntity<List<CartItemResponseDto>> getCartItems(@PathVariable Long cartId) {
        List<CartItemResponseDto> response = cartService.getCartItems(cartId);
        return ResponseEntity.ok(response);
    }

    /**
     * 사용자 장바구니 조회
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<CartResponseDto> getCartByUserId(@PathVariable Long userId) {
        CartResponseDto response = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(response);
    }
}
