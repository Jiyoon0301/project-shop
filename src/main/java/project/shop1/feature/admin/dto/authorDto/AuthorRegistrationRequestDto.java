package project.shop1.feature.admin.dto.authorDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
public class AuthorRegistrationRequestDto {

    @NotBlank(message = "작가의 이름을 입력해주세요.", groups=NotBlank.class)
    private String authorName;

    @Size(max = 10, min = 2,message = "국가는 10자리를 넘을 수 없습니다.")
    private String nation;

    private String authorIntro;

}
