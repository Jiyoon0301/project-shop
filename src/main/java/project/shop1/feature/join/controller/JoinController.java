package project.shop1.feature.join.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.shop1.common.ResponseDto;
import project.shop1.feature.join.dto.JoinRequestDto;
import project.shop1.feature.join.service.JoinService;
import project.shop1.common.validation.ValidationSequence;
import project.shop1.util.AddressService;
import project.shop1.util.MailService;

import java.net.URI;
import java.nio.charset.Charset;

@RestController
@RequiredArgsConstructor
@Slf4j
public class JoinController {

    private final JoinService joinService;
    private final MailService mailService;
    private final AddressService addressService;

    private String verificationCode;
    private Boolean verified = false; //이메일 인증 여부

    @PostMapping("/join")
    public ResponseEntity<ResponseDto> joinUser(@Validated(value = ValidationSequence.class) JoinRequestDto joinRequestDto) throws Exception{
        joinService.joinUser(joinRequestDto);
        return new ResponseEntity(new ResponseDto("회원가입이 정상적으로 완료되었습니다."), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/sendEmail") //인증번호 메일 발송
    public ResponseEntity<ResponseDto> Email(@RequestParam("email") String email){
        verificationCode = mailService.sendEmail(email).getAuthCode();
        return new ResponseEntity(new ResponseDto("인증번호가 발송되었습니다."), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/verificationMail") //번호 입력 및 인증
    public ResponseEntity<ResponseDto> verificationMail(@RequestParam("email") String email, @RequestParam("userAuthCode") String userAuthCode){
        verified = joinService.verificationMail(JoinRequestDto.builder().authCode(verificationCode).build(),userAuthCode);
        System.out.println(verified);
        if (verified==true){
            return new ResponseEntity(new ResponseDto("인증이 완료되었습니다."), new HttpHeaders(), HttpStatus.OK);
        } else { return new ResponseEntity(new ResponseDto("인증번호를 다시 확인해주세요."), new HttpHeaders(), HttpStatus.OK);}
    }

    @GetMapping("/searchAddress")
    public String searchAddress(String keyword){
        return addressService.searchAddress(keyword);
    }
}
