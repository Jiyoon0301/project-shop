package project.shop1.domain.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GetReviewsResponseDto {
    private Long id;
    private String content;
    private int rating;
    private String userName;
    private String regDate;
}
