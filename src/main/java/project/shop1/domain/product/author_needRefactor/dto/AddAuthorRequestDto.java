package project.shop1.domain.product.author_needRefactor.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddAuthorRequestDto {
    private Long id;
    private String name;
    private String nation;
    // book 정보
}
