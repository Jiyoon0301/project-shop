package project.shop1.domain.emailAuth.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.global.util.reponse.BooleanResponse;
import project.shop1.global.util.validation.ValidationSequence;
import project.shop1.domain.emailAuth.dto.EmailAuthRequestDto;
import project.shop1.domain.emailAuth.service.EmailAuthService;

@RestController
@RequiredArgsConstructor
public class EmailAuthController {

    private final EmailAuthService emailAuthService;

    /* 이메일 인증을 위한 인증번호 발송*/
    @PostMapping("/auth-Email/send")
    @PreAuthorize("permitAll()")
    public ResponseEntity<BooleanResponse> send(@Validated(value = ValidationSequence.class) @RequestBody EmailAuthRequestDto emailAuthRequestDto) {

        emailAuthService.sendEmail(emailAuthRequestDto);
        return ResponseEntity.ok(BooleanResponse.of(true));
    }
}
