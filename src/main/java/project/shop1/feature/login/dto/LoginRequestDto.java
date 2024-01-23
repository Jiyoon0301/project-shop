package project.shop1.feature.login.dto;


import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.Getter;

@Data
@Builder
@Getter
public class LoginRequestDto {

    private String userId;
    private String password;

}
