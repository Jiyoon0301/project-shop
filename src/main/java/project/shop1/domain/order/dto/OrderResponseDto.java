package project.shop1.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import project.shop1.domain.order.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponseDto {
    private Long orderId;
    private Long userId;
    private int totalPrice;
    private OrderStatus orderStatus;
    private List<OrderItemDto> orderItems;
    private LocalDateTime orderDate;

    @Data
    @Builder
    @AllArgsConstructor
    public static class OrderItemDto {
        private Long productId;
        private String productName;
        private int quantity;
        private int price;
    }
}
