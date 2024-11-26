package project.shop1.domain.review_needRefactor.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReviewRequestDto {

    private Long productId;

    private Long userId;

    // 리뷰 내용
    @NotBlank(message = "내용을 입력해주세요.", groups = NotBlank.class)
    private String content;

    // 평점
    @NotBlank(message = "평점을 선택해주세요.", groups = NotBlank.class)
    @Min(1)
    @Max(5)
    private int rating;
}
