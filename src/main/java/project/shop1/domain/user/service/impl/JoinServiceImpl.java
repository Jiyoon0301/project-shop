package project.shop1.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.common.exception.BusinessException;
import project.shop1.common.exception.ErrorCode;
import project.shop1.domain.emailAuth.service.EmailAuthService;
import project.shop1.domain.user.dto.GetUserResponseDto;
import project.shop1.domain.user.dto.JoinRequestDto;
import project.shop1.domain.user.entity.UserEntity;
import project.shop1.domain.user.repository.UserRepository;
import project.shop1.domain.user.service.UserService;
import project.shop1.entity.enums.UserRank;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class JoinServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailAuthService emailAuthService;

    @Override
    public Long getCurrentMember(Authentication authentication) {
        throw new UnsupportedOperationException("현재 사용자 조회는 UserServiceImpl에서 처리해야 합니다.");
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
    public GetUserResponseDto findUserById(Long id) {
        throw new UnsupportedOperationException("사용자 조회는 UserServiceImpl에서 처리해야 합니다.");
    }

    @Override
    public void deleteUser(Long id) {
        throw new UnsupportedOperationException("사용자 삭제는 UserServiceImpl에서 처리해야 합니다.");
    }
}
