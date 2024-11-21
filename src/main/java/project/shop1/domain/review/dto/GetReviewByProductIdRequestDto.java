package project.shop1.domain.review.dto;

import lombok.Data;

@Data
public class GetReviewByProductIdRequestDto {

    private Long productId;

    private int page;
}
