package project.shop1.domain.user.service;

import project.shop1.domain.user.dto.JoinRequestDto;
import project.shop1.domain.user.dto.JoinResponseDto;

public interface JoinService {

    JoinResponseDto join(JoinRequestDto joinRequestDto);
}
