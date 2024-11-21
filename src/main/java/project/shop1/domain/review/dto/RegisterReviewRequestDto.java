package project.shop1.domain.review.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterReviewRequestDto {

    private Long productId;

    /* 리뷰 내용 */
    private String content;

    /* 평점 */
    @NotBlank(message = "평점을 선택해주세요.", groups=NotBlank.class)
    private double rating;
}
