package project.shop1.feature.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.common.reponse.BooleanResponse;
import project.shop1.common.validation.ValidationSequence;
import project.shop1.entity.CartItem;
import project.shop1.feature.cart.dto.DeleteCartRequestDto;
import project.shop1.feature.cart.dto.FindAllCartItemsByUserRequestDto;
import project.shop1.feature.cart.dto.UpdateProductQuantityRequestDto;
import project.shop1.feature.cart.service.CartService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /* 회원의 장바구니 상품 리스트 조회 */
    @PostMapping("/cart/findAllCartItemsByUser") //findAllCartItemByUserRequestDto : String account
    public List<CartItem> findAllCartItemsByUser(@Validated(value = ValidationSequence.class) @RequestBody FindAllCartItemsByUserRequestDto findAllCartItemsByUserRequestDto){
        List<CartItem> result = cartService.findAllCartItemsByUser(findAllCartItemsByUserRequestDto);
        return result;
    }

    /* 장바구니에서 상품 삭제 */
    @PostMapping("/cart/deleteCart") //DeleteCartItem : Long CartItemId
    public ResponseEntity<BooleanResponse> deleteCart(@Validated(value = ValidationSequence.class) @RequestBody DeleteCartRequestDto deleteCartRequestDto){
        cartService.deleteCart(deleteCartRequestDto);
        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    /* 장바구니 상품 수량 변경 */
    @PostMapping("/cart/updateProductQuantity") //UpdateProductQuantityRequestDto : String account, Long cartItemId, int count
    public ResponseEntity<BooleanResponse> updateProductQuantity(@Validated(value = ValidationSequence.class) @RequestBody UpdateProductQuantityRequestDto updateProductQuantityRequestDto){
        cartService.updateProductQuantity(updateProductQuantityRequestDto);
        return ResponseEntity.ok(BooleanResponse.of(true));
    }




}
