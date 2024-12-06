package project.shop1.domain.order.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.shop1.domain.order.enums.OrderStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusUpdateRequestDto {
    @NotNull(message = "주문 상태는 필수입니다.")
    private OrderStatus status;
}
