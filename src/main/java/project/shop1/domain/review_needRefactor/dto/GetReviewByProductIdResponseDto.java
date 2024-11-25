package project.shop1.domain.review_needRefactor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GetReviewByProductIdResponseDto {

    private String userEntityName;
    private String content;
    private double rating;
    private LocalDateTime regDate;

}
