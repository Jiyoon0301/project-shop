package project.shop1.feature.order.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductInfoPairs {
    /* 각 상품 이름 */
    String productName;

    /* 각 상품 수량 */
    int productCount;

    /* 각 상품 가격 */
    int productPrice;
}
