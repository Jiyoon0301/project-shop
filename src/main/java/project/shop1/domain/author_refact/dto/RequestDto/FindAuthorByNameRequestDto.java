package project.shop1.domain.author_refact.dto.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAuthorByNameRequestDto {
    String name;
}
