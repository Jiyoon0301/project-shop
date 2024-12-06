package project.shop1.domain.user.service;

import project.shop1.domain.user.dto.response.GetUserResponseDto;

public interface UserService {

    GetUserResponseDto findUserById(Long id);

    void deleteUser(Long id);
}
