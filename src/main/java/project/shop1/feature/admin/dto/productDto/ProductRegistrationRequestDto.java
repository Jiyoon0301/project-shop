package project.shop1.feature.admin.dto.productDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductRegistrationRequestDto {

    @NotBlank(message = "책 제목을 입력해주세요.", groups=NotBlank.class)
    private String title;
    @NotBlank(message = "저자를 입력해주세요.", groups=NotBlank.class)
    private String authorName;
    @NotBlank(message = "가격을 입력해주세요.", groups=NotBlank.class)
    private int price;
    @NotBlank(message = "수량을 입력해주세요.", groups=NotBlank.class)
    private int stockQuantity;
    @NotBlank(message = "카테고리를 입력해주세요.", groups=NotBlank.class)
    private String category;


}
