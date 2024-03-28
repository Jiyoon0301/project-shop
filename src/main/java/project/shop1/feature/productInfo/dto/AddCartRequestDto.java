package project.shop1.feature.productInfo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddCartRequestDto {

    String account;

    Long productNumber;

    @NotBlank(message = "수량을 입력해주세요.", groups=NotBlank.class)
    int quantity;
}
