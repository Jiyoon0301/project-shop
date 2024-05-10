package project.shop1.feature.review.dto;

import lombok.Data;

@Data
public class GetReviewByProductIdRequestDto {

    private Long productId;

    private int page;
}
