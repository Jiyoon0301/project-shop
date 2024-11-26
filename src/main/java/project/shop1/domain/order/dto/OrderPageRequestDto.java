package project.shop1.domain.order.dto;

import lombok.Data;

@Data
public class OrderPageRequestDto {

    /* 장바구니 구매인지 확인 */
    Boolean isFromCartPage; // true, false

    /* 상품 - 무슨 상품을 주문하는지(상품 상세 페이지에서 구매) */
    Long bookId;

    /* 수량 - 상품을 몇 개 주문하는지 */
    int count;


}
