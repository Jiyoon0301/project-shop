package project.shop1.domain.auth.emailAuth.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.domain.auth.emailAuth.dto.request.AuthCodeRequestDto;
import project.shop1.domain.auth.emailAuth.dto.request.EmailAuthRequestDto;
import project.shop1.domain.auth.emailAuth.entity.EmailAuth;
import project.shop1.domain.auth.emailAuth.repository.EmailAuthRepository;
import project.shop1.domain.auth.emailAuth.service.EmailAuthService;
import project.shop1.global.exception.BusinessException;
import project.shop1.global.exception.ErrorCode;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailAuthServiceImpl implements EmailAuthService {

    private final EmailAuthRepository emailAuthRepository;
    private final JavaMailSender javaMailSender;
    private final Random random = new Random();

    @Override
    public void sendEmail(EmailAuthRequestDto emailAuthRequestDto) {
        String authCode = Integer.toString(random.nextInt(888888) + 111111);
        String email = emailAuthRequestDto.getEmail();

        EmailAuth emailAuth = EmailAuth.builder()
                .email(email)
                .authcode(authCode)
                .build();
        emailAuthRepository.save(emailAuth);

        // 이메일 발송
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setFrom("j1y00n000301@gmail.com");
        simpleMailMessage.setSubject("[쇼핑몰] 일반 회원가입 이메일 인증");
        simpleMailMessage.setText("쇼핑몰에 방문해주셔서 감사합니다.\n\n" + "인증번호는 " + authCode + " 입니다." + "\n\n 인증번호를 인증코드란에 입력해주세요.");
        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public Boolean validationAuthCode(AuthCodeRequestDto authCodeRequestDto) {
        String email = authCodeRequestDto.getEmail();
        String authcode = authCodeRequestDto.getAuthcode();

        Optional<EmailAuth> findEmailAuth = emailAuthRepository.findByEmail(email);

        if (findEmailAuth.isEmpty()) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "인증번호가 생성되지 않았습니다.");
        }

        EmailAuth emailAuth = findEmailAuth.get();

        if (!emailAuth.getAuthcode().equals(authcode)) {
            throw new BusinessException(ErrorCode.AUTHENTICATION_FAIL, "잘못된 인증번호입니다.");
        }
        return true;
    }

    @Override
    public EmailAuth findEmailAuthByEmail(String email) {
        return emailAuthRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "인증번호가 생성되지 않았습니다."));
    }
}
