package project.shop1.feature.myPage.getUserOrders.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemsPairs {

    /* 상품 이름 */
    private String productName;

    /* 상품 가격 */
    private int productPrice;

    /* 주문한 상품 수량 */
    private int productCount;

}
