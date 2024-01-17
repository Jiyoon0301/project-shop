package project.shop1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JoinRequestDto {

    @NotBlank(message = "아이디를 입력해 주세요.")
    private String userId;

    @NotBlank(message = "비밀번호 항목은 필수 입력값입니다.")
    @Size(min=8, max=16, groups = SizeChekingGroup.class,message = "비밀번호는 8자 이상 16자 이하여야합니다.")
    @Pattern(regexp = "~`!@#\\$%\\^\\(\\)*_\\-\\=\\{\\}\\[\\]\\|;:<>,\\.\\?/",
            groups = PatternCheckGroup.class,
            message = "비밀번호는 대소문자/숫자/특수문자 중 2가지 이상 조합이어야 합니다. \n" +
                    "입력 가능 특수문자 : ~`!@#\\$%\\^\\(\\)*_\\-\\=\\{\\}\\[\\]\\|;:<>,\\.\\?/ \n" +
                    "공백 입력 불가능 \n" +
                    "연속된 문자, 숫자 사용 불가능 \n" +
                    "동일한 문자, 숫자를 반복해서 사용 불가능 \n" +
                    "아이디 포함 불가능")    private String password;

    @NotEmpty(message = "이름 항목은 필수 입력값입니다.")
    private String name;
    @NotBlank(message = "휴대전화 번호를 입력해 주세요.")
    private String phoneNumber;
    @NotBlank(message = "이메일을 입력해 주세요.")
    private String email;
}
