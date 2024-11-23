package project.shop1.domain.user.service;

import org.springframework.security.core.Authentication;
import project.shop1.domain.user.dto.GetUserResponseDto;
import project.shop1.domain.user.dto.JoinRequestDto;

public interface UserService {
    Long getCurrentMember(Authentication authentication);

    void join(JoinRequestDto joinRequestDto);

    GetUserResponseDto findUserById(Long id);

    void deleteUser(Long id);
}
