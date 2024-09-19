package project.shop1.feature.join.service.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.shop1.common.exception.BusinessException;
import project.shop1.common.exception.ErrorCode;
import project.shop1.common.repository.UserRepository;
import project.shop1.entity.EmailAuth;
import project.shop1.entity.enums.UserRank;
import project.shop1.feature.join.dto.*;
import project.shop1.entity.UserEntity;
import project.shop1.feature.join.repository.JoinRepository;
import project.shop1.feature.join.service.JoinService;

import java.util.Random;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class JoinServiceImpl implements JoinService {

    private final JoinRepository joinRepository;
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final Random random = new Random();

    @Override
    @Transactional
    public void join(JoinRequestDto joinRequestDto) {
        String account = joinRequestDto.getAccount();
        String password=joinRequestDto.getPassword();
        String name = joinRequestDto.getName();
        String phoneNumber= joinRequestDto.getPhoneNumber();
        String email = joinRequestDto.getEmail();
        String inputAuthCode = joinRequestDto.getInputAuthCode();
        String address = joinRequestDto.getAddress();

        Optional<UserEntity> findUserEntity = userRepository.findUserEntityByAccount(account);

        //아이디 중복 확인
        if(findUserEntity.isPresent()){
            throw new BusinessException(ErrorCode.RESOURCE_CONFLICT, "이미 존재하는 아이디입니다.");
        }

        Optional<EmailAuth> findEmailAuth = joinRepository.findEmailAuthByEmail(email);

//        //이메일 인증 확인
//        if (!findEmailAuth.get().getAuthcode().equals(inputAuthCode)){
//            throw new BusinessException(ErrorCode.AUTHENTICATION_FAIL, "인증번호를 다시 확인해주세요.");
//        }

        //비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);


        UserEntity userEntity = UserEntity.builder()
                .account(account)
                .password(encodedPassword)
                .name(name)
                .phoneNumber(phoneNumber)
                .email(email)
                .loginType("internal")
                .userRank(UserRank.MEMBER)
                .address(address)
                .build();

        userEntity.addRole("USER");

        joinRepository.saveUserEntity(userEntity);
    }

    @Override
    @Transactional
    public void authEmail(EmailAuthRequestDto emailAuthRequestDto) {
        String authCode = Integer.toString(random.nextInt(888888) + 111111);

        String email = emailAuthRequestDto.getEmail();

        EmailAuth emailAuth = EmailAuth.builder()
                .email(email)
                .authcode(authCode)
                .build();
        joinRepository.saveEmailAuth(emailAuth);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setFrom("j1y00n000301@gmail.com");
        simpleMailMessage.setSubject("[쇼핑몰] 일반 회원가입 이메일 인증");
        simpleMailMessage.setText("쇼핑몰에 방문해주셔서 감사합니다.\n\n" + "인증번호는 " + authCode + " 입니다." + "\n\n 인증번호를 인증코드란에 입력해주세요.");

        javaMailSender.send(simpleMailMessage);

    }

    @Override
    public void validationAuthCode(AuthCodeRequestDto authCodeRequestDto) {
        String email = authCodeRequestDto.getEmail();
        String authcode = authCodeRequestDto.getAuthcode();

        Optional<EmailAuth> findEmailAuth = joinRepository.findEmailAuthByEmail(email);

        if (findEmailAuth.isEmpty()) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "인증번호가 생성되지 않았습니다.");
        }

        EmailAuth emailAuth = findEmailAuth.get();

        if (!emailAuth.getAuthcode().equals(authcode)) {
            throw new BusinessException(ErrorCode.AUTHENTICATION_FAIL, "잘못된 인증번호입니다.");
        }

    }


}
