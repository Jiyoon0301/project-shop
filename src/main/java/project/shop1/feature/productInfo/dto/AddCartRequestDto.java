package project.shop1.feature.productInfo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddCartRequestDto {

    String account;

    Long productNumber;

    @NotBlank(message = "아이디를 입력해 주세요.", groups=NotBlank.class)
    int quantity;
}
