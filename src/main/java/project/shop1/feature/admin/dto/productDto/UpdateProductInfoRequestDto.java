package project.shop1.feature.admin.dto.productDto;

import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;

@Data
public class UpdateProductInfoRequestDto {
    Long productNumber;
    String title;
    String authorName;
    int price;
    int stockQuantity;
    String category;
}
