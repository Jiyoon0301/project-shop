package project.shop1.domain.cart.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddProductRequestDto {
    private Long productId;
    private int quantity;
}
