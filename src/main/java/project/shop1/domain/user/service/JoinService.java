package project.shop1.domain.user.service;

import project.shop1.domain.user.dto.request.JoinRequestDto;
import project.shop1.domain.user.dto.response.JoinResponseDto;

public interface JoinService {

    JoinResponseDto join(JoinRequestDto joinRequestDto);
}
