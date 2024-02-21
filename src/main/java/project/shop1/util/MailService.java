package project.shop1.util;

import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import project.shop1.feature.join.dto.JoinRequestDto;

import java.util.Random;

@Service
public class MailService {

    @Autowired
    private JavaMailSender emailSender;

    public JoinRequestDto sendEmail(String email){

        SimpleMailMessage message = new SimpleMailMessage();
        String authCode = createRandomPw();
        //메일 제목
        message.setSubject("인증번호가 발송됐습니다.");
        //메일 본문
        message.setText("메일 확인 인증 번호는 "+authCode+" 입니다. 정확히 입력해주세요."); //메일 본문 내용
        //메일 도착 주소 설정
        message.setTo(email);

        emailSender.send(message);

        JoinRequestDto joinRequestDto = JoinRequestDto.builder()
                .authCode(authCode)
                .build();

        return joinRequestDto;
    }


    // 6자리 인증번호 생성
    public String createRandomPw(){
        Random random = new Random();

        // 100000 이상 999999 이하의 랜덤 숫자 생성
        int randomNumber = random.nextInt(900000) + 100000;

        // 생성된 랜덤 숫자 출력
        System.out.println("생성된 랜덤 숫자: " + randomNumber);


        return String.valueOf(randomNumber);
    }
}
