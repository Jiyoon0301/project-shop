package project.shop1.domain.cart.dto;

import lombok.Data;

@Data
public class UpdateQuantityByOneRequestDto {

    /* 장바구니 목록 중 어떤 아이템의 수량 변경할 건지 */
    Long cartItemId;

    /* 수량 증가/감소 */
    String type;
}
