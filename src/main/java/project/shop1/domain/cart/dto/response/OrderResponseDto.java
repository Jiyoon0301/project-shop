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
    private List<CartItemResponseDto> items;
    private int totalAmount;
    private LocalDateTime orderDate;
}
