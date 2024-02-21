package project.shop1.feature.join.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import project.shop1.common.validation.NotBlankGroup;
import project.shop1.common.validation.PatternCheckGroup;
import project.shop1.common.validation.SizeCheckGroup;

@Data
@Builder
public class JoinRequestDto {

    @NotBlank(message = "아이디를 입력해 주세요.", groups=NotBlank.class)
    private String userId;

    @NotBlank(message = "비밀번호 항목은 필수 입력값입니다.", groups=NotBlank.class)
    @Size(min=8, max=16, groups = SizeCheckGroup.class,message = "비밀번호는 8자 이상 16자 이하여야합니다.")
//    @Pattern(regexp = "~`!@#\\$%\\^\\(\\)*_\\-\\=\\{\\}\\[\\]\\|;:<>,\\.\\?/",
//            groups = PatternCheckGroup.class,
//            message = "비밀번호는 대소문자/숫자/특수문자 중 2가지 이상 조합이어야 합니다. \n" +
//                    "입력 가능 특수문자 : ~`!@#\\$%\\^\\(\\)*_\\-\\=\\{\\}\\[\\]\\|;:<>,\\.\\?/ \n" +
//                    "공백 입력 불가능 \n" +
//                    "연속된 문자, 숫자 사용 불가능 \n" +
//                    "동일한 문자, 숫자를 반복해서 사용 불가능 \n" +
//                    "아이디 포함 불가능")
    private String password;

    @NotBlank(message = "이름 항목은 필수 입력값입니다.",groups = NotBlankGroup.class)
    private String name;
    @NotBlank(message = "휴대전화 번호를 입력해 주세요.", groups = NotBlankGroup.class)
    private String phoneNumber;
    @NotBlank(message = "이메일을 입력해 주세요.", groups = NotBlankGroup.class)
    private String email;

    private String authCode;
}
