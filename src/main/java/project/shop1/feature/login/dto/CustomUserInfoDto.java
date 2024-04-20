package project.shop1.feature.login.dto;

import lombok.*;
import project.shop1.entity.RoleType;

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
