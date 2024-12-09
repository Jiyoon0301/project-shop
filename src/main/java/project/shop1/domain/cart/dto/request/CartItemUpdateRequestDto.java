package project.shop1.domain.cart.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemUpdateRequestDto {
    private int quantity; // 수량
    private int price;    // 가격
}
