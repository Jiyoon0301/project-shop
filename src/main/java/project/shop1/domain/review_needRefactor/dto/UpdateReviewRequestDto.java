package project.shop1.domain.review_needRefactor.dto;

import lombok.Data;

@Data
public class UpdateReviewRequestDto {

    private Long productId;

    private String content;

    private double rating;
}
