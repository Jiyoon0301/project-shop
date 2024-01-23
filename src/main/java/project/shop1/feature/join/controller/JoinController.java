package project.shop1.feature.join.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.common.ResponseDto;
import project.shop1.feature.join.dto.JoinRequestDto;
import project.shop1.feature.join.service.JoinService;
import project.shop1.common.validation.ValidationSequence;

@RestController
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public ResponseEntity<ResponseDto> joinUser(@Validated(value = ValidationSequence.class) JoinRequestDto joinRequestDto) throws Exception{
        joinService.joinUser(joinRequestDto);
        return new ResponseEntity(new ResponseDto("회원가입이 정상적으로 완료되었습니다."), new HttpHeaders(), HttpStatus.OK);
    }
}
