package project.shop1.feature.order.dto;

import lombok.Data;

@Data
public class OrderPageRequestDto {

    /* 장바구니 구매 버튼인지 확인 */
    Boolean isFromCartPage; // true, false

    /* 상품 - 무슨 상품을 주문하는지(상품 상세 페이지에서 구매하기 버튼) */
    Long bookId;

    /* 수량 - 상품을 몇 개 주문하는지 */
    int count;


}
