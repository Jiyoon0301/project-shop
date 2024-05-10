package project.shop1.feature.admin.dto.authorDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateAuthorInfoRequestDto {
    @NotBlank(message = "작가 번호는 필수 입력사항입니다.", groups = NotBlank.class)
    Long authorNumber;
    @NotBlank(message = "작가의 이름을 입력해주세요.", groups=NotBlank.class)
    String authorName;
    String nation;
    String authorIntro;
}
