package project.shop1.domain.cart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class OrderResponseDto {
    private Long orderId;
    private Long userId;
    private LocalDateTime orderDate;
    private int totalPrice;
    private List<OrderItemResponseDto> items;
}
