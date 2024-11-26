package project.shop1.domain.review.dto;

import lombok.Data;

@Data
public class GetReviewsRequestDto {
    private Integer rating; // 특정 평점 필터 (옵션)
    private int page = 0;
    private String sortBy;  // 정렬 기준: "rating" 또는 "date" (옵션)
}
