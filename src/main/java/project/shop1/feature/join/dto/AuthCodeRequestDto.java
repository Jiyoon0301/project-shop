package project.shop1.feature.join.dto;

import lombok.Data;

@Data
public class AuthCodeRequestDto {
    private String authcode;
    private String email;
}
