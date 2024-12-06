package project.shop1.domain.auth.login.common;

import lombok.*;
import project.shop1.domain.user.enums.RoleType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserInfoDto {

    private Long id;

    private String account;

    private String password;

    private String name;

    private RoleType role;
}
