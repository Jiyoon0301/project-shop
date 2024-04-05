package project.shop1.feature.order.dto;

import lombok.Builder;
import project.shop1.entity.Delivery;
import project.shop1.entity.OrderItem;
import project.shop1.entity.UserEntity;
import project.shop1.entity.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
public class SubmitOrderResponseDto {

    /* 주문자 */
    UserEntity userEntity;

    /* 배송지 정보 */
    String address;

    /* 주문 상태 */
    OrderStatus orderStatus; // [ORDER, CANCEL]

    /* 주문 날짜 */
    LocalDateTime orderDate;

    /* 주문 아이템 목록 */
    List<OrderItem> orderItems = new ArrayList<>();

    /* 배송 정보 */
    Delivery delivery;

}
