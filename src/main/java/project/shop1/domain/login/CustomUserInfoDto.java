package project.shop1.domain.login;

import lombok.*;
import project.shop1.entity.enums.RoleType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CustomUserInfoDto {

    private Long id;

    private String account;

    private String password;

    private String name;

    private RoleType role;

}
