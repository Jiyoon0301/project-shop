package project.shop1.domain.auth.login.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GoogleUserLoginResponseDto {

    private String googleEmail;
}
