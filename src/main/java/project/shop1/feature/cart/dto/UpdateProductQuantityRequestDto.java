package project.shop1.feature.cart.dto;

import lombok.Data;

@Data
public class UpdateProductQuantityRequestDto {

    /* 장바구니 주인 userEntity userAccount */
    String account;

    /* 장바구니 목록 중 어떤 아이템의 수량 변경할 건지 */
    Long cartItemId;

    /* 수량 얼마나 변경할 건지 */
    int count;
}
