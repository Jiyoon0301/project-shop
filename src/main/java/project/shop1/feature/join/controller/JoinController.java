package project.shop1.feature.join.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.shop1.common.reponse.BooleanResponse;
import project.shop1.feature.join.dto.EmailAuthRequestDto;
import project.shop1.feature.join.dto.JoinRequestDto;
import project.shop1.feature.join.service.JoinService;
import project.shop1.common.validation.ValidationSequence;

@RestController
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    /* 회원가입*/
    @PostMapping("/join") // String account, String password, String name, String phoneNumber, String email
    public ResponseEntity<BooleanResponse> join(@Validated(value = ValidationSequence.class) @RequestBody JoinRequestDto joinRequestDto) {
        joinService.join(joinRequestDto);

        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    /* 이메일 인증을 위한 인증번호 발송*/
    @PostMapping("/auth-email")
    public ResponseEntity<BooleanResponse> authEmail(@Validated(value = ValidationSequence.class) @RequestBody EmailAuthRequestDto emailAuthRequestDto) {
        joinService.authEmail(emailAuthRequestDto);

        return ResponseEntity.ok(BooleanResponse.of(true));
    }


}
