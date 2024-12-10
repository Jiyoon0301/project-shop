package project.shop1.domain.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.shop1.domain.cart.dto.request.AddProductRequestDto;
import project.shop1.domain.cart.dto.request.CartItemRequestDto;
import project.shop1.domain.cart.dto.request.CartItemUpdateRequestDto;
import project.shop1.domain.cart.dto.response.CartItemResponseDto;
import project.shop1.domain.cart.dto.response.CartResponseDto;
import project.shop1.domain.cart.dto.response.OrderResponseDto;
import project.shop1.domain.cart.service.CartService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    /**
     * 장바구니에 상품 추가
     *
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
     *
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
     *
     * @param cartId
     * @return
     */
    @GetMapping("/{cartId}/items")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<CartItemResponseDto>> getCartItems(@PathVariable Long cartId) {
        List<CartItemResponseDto> response = cartService.getCartItems(cartId);
        return ResponseEntity.ok(response);
    }

    /**
     * 사용자 장바구니 조회
     *
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CartResponseDto> getCartByUserId(@PathVariable Long userId) {
        CartResponseDto response = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * 장바구니 상품 업데이트
     *
     * @param cartId
     * @param itemId
     * @param request
     * @return
     */
    @PutMapping("/{cartId}/items/{itemId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CartItemResponseDto> updateCartItem(
            @PathVariable Long cartId,
            @PathVariable Long itemId,
            @RequestBody CartItemUpdateRequestDto request) {
        CartItemResponseDto response = cartService.updateCartItem(cartId, itemId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * 장바구니 상품 수량 업데이트
     *
     * @param cartId
     * @param itemId
     * @param quantity
     * @return
     */
    @PatchMapping("/{cartId}/items/{itemId}/quantity")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CartItemResponseDto> updateQuantity(
            @PathVariable Long cartId,
            @PathVariable Long itemId,
            @RequestParam int quantity) {
        CartItemResponseDto response = cartService.updateQuantity(cartId, itemId, quantity);
        return ResponseEntity.ok(response);
    }

    /**
     * 장바구니에서 상품 삭제
     *
     * @param cartId
     * @param itemId
     * @return
     */
    @DeleteMapping("/{cartId}/items/{itemId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        cartService.removeItemFromCart(cartId, itemId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 장바구니 초기화
     *
     * @param cartId
     * @return
     */
    @DeleteMapping("/{cartId}/clear")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 장바구니 내용을 주문으로 변환
     *
     * @param cartId
     * @return
     */
    @PostMapping("/{cartId}/convert-to-order")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderResponseDto> convertCartToOrder(@PathVariable Long cartId) {
        OrderResponseDto response = cartService.convertCartToOrder(cartId);
        return ResponseEntity.ok(response);
    }
}
