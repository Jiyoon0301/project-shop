package project.shop1.domain.user.service;

import project.shop1.domain.user.dto.GetUserResponseDto;
import project.shop1.domain.user.dto.JoinRequestDto;

public interface UserService {

    void join(JoinRequestDto joinRequestDto);

    GetUserResponseDto findUserById(Long id);

    void deleteUser(Long id);
}
