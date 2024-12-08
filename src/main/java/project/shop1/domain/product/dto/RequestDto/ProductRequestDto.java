package project.shop1.domain.product.dto.RequestDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {

    @NotBlank(message = "상품 이름을 입력해주세요.")
    private String title;

    @NotNull(message = "가격을 입력해주세요.")
    @Min(value = 0, message = "가격은 최소 0원입니다.")
    private int price;

    private int stockQuantity;

    private String authorName;

    private String category;
}
