package project.shop1.domain.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JoinResponseDto {

    private Long id;
    private String account;
    private String name;
    private String email;
    private String phoneNumber;
    private List<String> roles;
}
