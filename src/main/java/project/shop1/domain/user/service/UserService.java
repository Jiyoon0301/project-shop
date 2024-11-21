package project.shop1.domain.user.service;

import org.springframework.security.core.Authentication;
import project.shop1.domain.user.dto.JoinRequestDto;
import project.shop1.domain.user.dto.SearchRequestDto;
import project.shop1.domain.user.dto.SearchResponseDto;

public interface UserService {
    Long getCurrentMember(Authentication authentication);

    void join(JoinRequestDto joinRequestDto);

    SearchResponseDto searchByAccount(SearchRequestDto searchRequestDto);
}
