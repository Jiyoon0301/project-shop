package project.shop1.feature.admin.dto.userDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDto {

    @NotBlank
    private String account;

    @NotBlank
    private String password;


}
