package project.shop1.feature.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetProductsByRankingResponseDto {

    private String bookTitle;
    private String bookCategory;
    private double bookRating;
}
