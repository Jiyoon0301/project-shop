package project.shop1.domain.auth.login.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NaverUserLoginResponseDto {

    private String naverEmail; //=account
    private String id; //=password
}
