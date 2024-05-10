package project.shop1.feature.login.naver.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NaverAccessTokenRequestDto {

    private String grant_type;
    private String client_id;
    private String client_secret;
    private String code;
    private String state;
}
