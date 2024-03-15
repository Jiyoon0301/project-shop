package project.shop1.feature.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
public class AuthorRequestDto {

    @Size(min = 3)
    private String authorName;

    @Size(max = 10, min = 2,message = "국가는 10자리를 넘을 수 없습니다.")
    private String nation;

    private String authorIntro;

}
