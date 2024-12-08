package project.shop1.domain.cart.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddCartRequestDto {

    private Long productId;

    @NotBlank(message = "수량을 입력해주세요.", groups=NotBlank.class)
    private int quantity;
}
