package project.shop1.domain.product.author_needRefactor.dto.ResponseDto;

import lombok.Builder;
import lombok.Data;
import project.shop1.domain.product.entity.Book;

import java.util.List;

@Data
@Builder
public class AddAuthorResponseDto {

    private Long id;
    private String name;
    private String nation;
    private List<Book> books;
}
