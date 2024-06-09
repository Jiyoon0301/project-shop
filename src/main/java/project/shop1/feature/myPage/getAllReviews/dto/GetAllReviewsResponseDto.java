package project.shop1.feature.myPage.getAllReviews.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class GetAllReviewsResponseDto {

    private String bookTitle;
    private String content;
    private double rating;
    private LocalDateTime regDate;

}
