package project.shop1.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.common.exception.BusinessException;
import project.shop1.common.exception.ErrorCode;
import project.shop1.domain.emailAuth.repository.EmailAuthRepository;
import project.shop1.domain.emailAuth.dto.AuthCodeRequestDto;
import project.shop1.domain.emailAuth.dto.EmailAuthRequestDto;
import project.shop1.domain.emailAuth.service.EmailAuthService;
import project.shop1.domain.user.dto.JoinRequestDto;
import project.shop1.domain.user.dto.SearchRequestDto;
import project.shop1.domain.user.dto.SearchResponseDto;
import project.shop1.domain.user.repository.UserRepository;
import project.shop1.domain.user.service.UserService;
import project.shop1.domain.user.entity.UserEntity;
import project.shop1.domain.emailAuth.entity.EmailAuth;
import project.shop1.entity.enums.UserRank;

import java.util.Optional;
import java.util.Random;

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
    public SearchResponseDto searchByAccount(SearchRequestDto searchRequestDto) {
        String account = searchRequestDto.getAccount();
        String name = userRepository.findByAccount(account).get().getName();
        return new SearchResponseDto(name);
    }
}
