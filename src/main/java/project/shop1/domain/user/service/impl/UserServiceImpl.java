package project.shop1.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.common.exception.BusinessException;
import project.shop1.common.exception.ErrorCode;
import project.shop1.domain.emailAuth.service.EmailAuthService;
import project.shop1.domain.user.dto.GetUserResponseDto;
import project.shop1.domain.user.dto.JoinRequestDto;
import project.shop1.domain.user.repository.UserRepository;
import project.shop1.domain.user.service.UserService;
import project.shop1.domain.user.entity.UserEntity;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailAuthService emailAuthService;

    /* 로그인되어있는 회원 id 반환 */
    @Override
    public Long getCurrentMember(Authentication authentication) {
        UserEntity userEntity = userRepository.findByAccount(authentication.getName()).get();
        return userEntity.getId();
    }

    @Override
    public void join(JoinRequestDto joinRequestDto) {
        throw new UnsupportedOperationException("회원가입은 JoinServiceImpl에서 처리해야 합니다.");
    }

    @Override
    public GetUserResponseDto findUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "사용자를 찾을 수 없습니다."));

        return GetUserResponseDto.builder()
                .id(userEntity.getId())
                .account(userEntity.getAccount())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .phoneNumber(userEntity.getPhoneNumber())
                .roles(userEntity.getRoles())
                .build();
    }

    @Override
    public void deleteUser(Long id) {

    }
}
