package project.shop1.domain.product.dto.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateRequestDto {

    private String title;
    private Integer price;
    private Integer stockQuantity;
    private String authorName;
    private String category;
}
