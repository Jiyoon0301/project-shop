package project.shop1.feature.cart.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.common.reponse.BooleanResponse;
import project.shop1.common.validation.ValidationSequence;
import project.shop1.entity.CartItem;
import project.shop1.feature.cart.dto.*;
import project.shop1.feature.cart.service.CartService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /* 회원의 장바구니 상품 리스트 조회 */
    @PostMapping("/cart/find-all-cart-items-by-user") //findAllCartItemByUserRequestDto : String account //java.lang.StackOverflowError
    public List<CartItem> findAllCartItemsByUser(HttpServletRequest request) {
        List<CartItem> result = cartService.findAllCartItemsByUser(request);
        return result;
    }

    /* 장바구니에서 체크된 상품들 총 가격 */
    @PostMapping("/cart/total-price") //List<Long> cartItemsId
    public int totalPrice(@Validated(value = ValidationSequence.class) @RequestBody TotalPriceRequestDto totalPriceRequestDto) {
        return cartService.totalPrice(totalPriceRequestDto);
    }

    /* 장바구니에 상품 추가 */
    @PostMapping("/cart/add-cart") //AddCartRequestDto : String account, Long productNumber, int quantity
    public ResponseEntity<BooleanResponse> addCart(@Validated(value = ValidationSequence.class) @RequestBody AddCartRequestDto addCartRequestDto, HttpServletRequest request){
        cartService.addCart(addCartRequestDto, request);
        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    /* 장바구니에서 상품 삭제 */
    @PostMapping("/cart/delete-cart") //DeleteCartItem : Long cartItemId
    public ResponseEntity<BooleanResponse> deleteCart(@Validated(value = ValidationSequence.class) @RequestBody DeleteCartRequestDto deleteCartRequestDto){
        cartService.deleteCart(deleteCartRequestDto);
        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    /* 장바구니 상품 수량 변경 */
    @PostMapping("/cart/update-quantity-by-one") //UpdateProductQuantityRequestDto : Long cartItemId, String type = up/down
    public ResponseEntity<BooleanResponse> updateQuantityByOne(@Validated(value = ValidationSequence.class) @RequestBody UpdateQuantityByOneRequestDto updateQuantityByOneRequestDto){
        cartService.updateProductQuantity(updateQuantityByOneRequestDto);
        return ResponseEntity.ok(BooleanResponse.of(true));
    }




}
