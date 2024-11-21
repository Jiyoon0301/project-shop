package project.shop1.domain.productSearch.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SearchBookByRankingRequestDto {

    @NotBlank(message = "페이지 당 표시량을 입력해주세요.", groups=NotBlank.class)
    int amount;
    @NotBlank(message = "페이지 수를 입력해주세요.", groups=NotBlank.class)
    int pageNumber;
}
