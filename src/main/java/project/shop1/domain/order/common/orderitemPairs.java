package project.shop1.domain.order.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import project.shop1.domain.product_refact.entity.Book;
import project.shop1.domain.order.entity.Order;

@Data
@AllArgsConstructor
public class orderitemPairs {

    /* 상품 */
    private Book book;

    /* 주문 */
    private Order order;

    /* 주문 가격 */
    private int orderPrice;

    /* 주문 수량 */
    private int count;


}
