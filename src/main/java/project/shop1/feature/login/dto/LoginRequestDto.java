package project.shop1.feature.login.dto;


import lombok.*;

@Data
@NoArgsConstructor
public class LoginRequestDto {

    private String account;
    private String password;

}
