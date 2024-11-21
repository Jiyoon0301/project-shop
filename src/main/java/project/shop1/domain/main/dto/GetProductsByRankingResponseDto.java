package project.shop1.domain.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetProductsByRankingResponseDto {

    private String bookTitle;
    private String bookCategory;
    private double bookRating;
}
