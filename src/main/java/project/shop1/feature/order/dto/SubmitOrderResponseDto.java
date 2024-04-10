package project.shop1.feature.order.dto;

import lombok.Builder;
import lombok.Data;
import project.shop1.entity.Delivery;
import project.shop1.entity.OrderItem;
import project.shop1.entity.UserEntity;
import project.shop1.entity.enums.OrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class SubmitOrderResponseDto {

    /* 주문 번호 */
    Long orderId;

    /* 주문 날짜 */
    LocalDate orderDate;

    /* 주문 아이템 리스트 정보 */
    List<String> bookName = new ArrayList<>();
    List<Integer> count = new ArrayList<>();
    List<Integer> price = new ArrayList<>();
//    List<OrderItem> orderItems = new ArrayList<>();

    /* 배송 정보 */
    String userEntityName;
    String userEntityPhoneNumber;
    String address;

    /* 주문 상태 */
    OrderStatus orderstatus;

    /* 배송 정보 */
    Delivery delivery;

    /* 결제 정보 */
    int totalProductPrice;
    int deliveryFee;
    int totalPrice;

}
