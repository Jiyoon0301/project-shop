package project.shop1.domain.review_needRefactor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

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
