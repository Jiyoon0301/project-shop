package project.shop1.feature.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDto {

    @NotBlank
    private String accoutn;

    @NotBlank
    private String password;


}
