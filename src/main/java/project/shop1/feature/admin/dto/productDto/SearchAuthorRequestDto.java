package project.shop1.feature.admin.dto.productDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SearchAuthorRequestDto {

    @NotBlank(message = "작가의 이름을 입력해주세요.", groups=NotBlank.class)
    private String authorName;
}
