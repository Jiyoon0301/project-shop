package project.shop1.domain.product.productSearch_needRefactor.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class findByKeywordAndCategoryRequestDto {

    /* 검색 기준 */
    String criteria;
    /* 검색어 - 제목, 작가 */
    String keyword;
    /* 카테고리 */
    String category;
    /* 결과물 개수 */
    @NotBlank(message = "페이지 당 표시량을 입력해주세요.", groups=NotBlank.class)
    int amount;
    /* 페이지 */
    @NotBlank(message = "페이지 수를 입력해주세요.", groups=NotBlank.class)
    int pageNumber;
}
