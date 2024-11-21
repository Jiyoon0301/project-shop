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
    @Transactional
    public void join(JoinRequestDto joinRequestDto) {
        String account = joinRequestDto.getAccount();
        String password = joinRequestDto.getPassword();
        String name = joinRequestDto.getName();
        String phoneNumber = joinRequestDto.getPhoneNumber();
        String email = joinRequestDto.getEmail();
//        String inputAuthCode = joinRequestDto.getInputAuthCode();
//        String address = joinRequestDto.getAddress();

        Optional<UserEntity> findUserEntity = userRepository.findByAccount(account);

        //아이디 중복 확인
        if (findUserEntity.isPresent()) {
            throw new BusinessException(ErrorCode.RESOURCE_CONFLICT, "이미 존재하는 아이디입니다.");
        }

        //이메일 인증 확인 (이 부분을 EmailAuthService로 위임)
        // emailAuthService.validationAuthCode(inputAuthCode);

        //비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        UserEntity userEntity = buildUserEntity(account, encodedPassword, name, phoneNumber, email);
        userEntity.addRole("USER");

        UserEntity userEntity1 = userRepository.save(userEntity);
    }

    private UserEntity buildUserEntity(String account, String password, String name, String phoneNumber, String email) {
        return UserEntity.builder()
                .account(account)
                .password(password)
                .name(name)
                .phoneNumber(phoneNumber)
                .email(email)
                .loginType("internal")
                .userRank(UserRank.MEMBER)
                .build();
    }

    @Override
    public SearchResponseDto searchByAccount(SearchRequestDto searchRequestDto) {
        String account = searchRequestDto.getAccount();
        String name = userRepository.findByAccount(account).get().getName();
        return new SearchResponseDto(name);
    }
}
