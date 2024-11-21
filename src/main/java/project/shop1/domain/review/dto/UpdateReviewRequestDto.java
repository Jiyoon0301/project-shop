package project.shop1.domain.review.dto;

import lombok.Data;

@Data
public class UpdateReviewRequestDto {

    private Long productId;

    private String content;

    private double rating;
}
