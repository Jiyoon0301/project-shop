package project.shop1.domain.order.dto;

import lombok.Builder;
import lombok.Data;
import project.shop1.domain.order.common.ProductInfoPairs;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class OrderPageResponseDto {
    /* 회원 이름 */
    String userEntityname;

    /* 회원 연락처 */
    String userEntityPhoneNumber;

    /* 회원 주소 */
    String userEntityAddress;

    List<ProductInfoPairs> productInfoPairs = new ArrayList<>();

//    /* 각 상품 이름 */
//    String productName;
//
//    /* 각 상품 수량 */
//    int productCount;
//
//    /* 각 상품 가격 */
//    int productPrice;

    /* 총 금액 */
    int totalPrice;

    /* 배송비 */
    int deliveryFee;

    /* 결제할 금액 */
    int amountToPay;
}
