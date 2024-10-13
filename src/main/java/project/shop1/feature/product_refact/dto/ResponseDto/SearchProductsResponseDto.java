package project.shop1.feature.product_refact.dto.ResponseDto;

import lombok.Builder;
import lombok.Data;
import project.shop1.entity.Book;

import java.util.List;

@Data
@Builder
public class SearchProductsResponseDto {

    private List<Book> books;


}
