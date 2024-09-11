package project.shop1.feature.myPage.getUserOrders.dto;

import lombok.Builder;
import lombok.Data;
import project.shop1.entity.enums.DeliveryStatus;
import project.shop1.entity.enums.OrderStatus;
import project.shop1.feature.myPage.getUserOrders.common.OrderItemsPairs;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class GetUserOrdersResponseDto {

    /* 주문 날짜 */
    private LocalDate orderdate;

    /* 배송 상태 (주문 취소했으면 주문 상태) */
    private DeliveryStatus deliveryStatus;
    private OrderStatus orderStatus;

    /* 주문 아이템과 정보 리스트 */
    private List<OrderItemsPairs> orderItemsPairs = new ArrayList<>();

}
