package project.shop1.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JoinRequestDto {

    private String userId;
    private String password;
    @NotEmpty
    private String name;
    private String phoneNumber;
    private String email;
}
