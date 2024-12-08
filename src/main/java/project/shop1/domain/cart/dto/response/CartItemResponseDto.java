package project.shop1.domain.cart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponseDto {
    private Long itemId;
    private Long productId;
    private String title;
    private int quantity;
    private int price;
    private int totalPrice;
}
