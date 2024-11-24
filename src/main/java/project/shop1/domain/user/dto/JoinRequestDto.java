package project.shop1.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.shop1.global.util.validation.NotBlankGroup;

@Data
@NoArgsConstructor
public class JoinRequestDto {

    @NotBlank(message = "아이디를 입력해 주세요.", groups=NotBlank.class)
    private String account;

    @NotBlank(message = "비밀번호 항목은 필수 입력값입니다.", groups=NotBlank.class)
//    @Size(min=8, max=16, groups = SizeCheckGroup.class,message = "비밀번호는 8자 이상 16자 이하여야합니다.")

    private String password;

    @NotBlank(message = "이름 항목은 필수 입력값입니다.",groups = NotBlankGroup.class)
    private String name;
    @NotBlank(message = "휴대전화 번호를 입력해 주세요.", groups = NotBlankGroup.class)
    private String phoneNumber;

    @Email(message = "이메일 양식을 맞춰주세요.")
    @NotBlank(message = "이메일을 입력해 주세요.", groups = NotBlankGroup.class)
    private String email;

//    @NotBlank(message = "인증번호를 입력해 주세요.", groups = NotBlankGroup.class)
    private String inputAuthCode;

    private String address;

    public JoinRequestDto(String account, String password, String name, String phoneNumber, String email) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
