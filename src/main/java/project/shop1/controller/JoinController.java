package project.shop1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.common.ResponseDto;
import project.shop1.dto.JoinRequestDto;
import project.shop1.service.JoinService;

@RestController
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public ResponseEntity<ResponseDto> joinUser(JoinRequestDto joinRequestDto) throws Exception{
        joinService.joinUser(joinRequestDto);
        return new ResponseEntity(new ResponseDto("회원가입이 정상적으로 완료되었습니다."), new HttpHeaders(), HttpStatus.OK);
    }
}
