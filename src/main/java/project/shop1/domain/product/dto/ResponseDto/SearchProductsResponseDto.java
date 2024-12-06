package project.shop1.domain.product.dto.ResponseDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchProductsResponseDto {

    private String bookTitle;
    private String authorName;
    private int price;
    private int stockQuantity;
}
