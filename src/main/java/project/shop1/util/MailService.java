package project.shop1.util;

import lombok.extern.flogger.Flogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MailService {

    @Autowired
    private JavaMailSender emailSender;

    public static final String ePw = createKey();

    public void sendEmail(String email){
        //단순 문자 메일을 보낼 수 있는 객체 생성
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("인증번호가 발송됐습니다."); //메일 제목
        message.setTo(email);
        message.setText("본인확인 인증번호는 ["+createRandomPw()+"]입니다. 정확히 입력해주세요."); //메일 본문 내용

        javaMailSender.send(message);
    }


    // 6자리 인증번호 생성
    public int createRandomPw(){
        Random random = new Random();
        int checkNum = random.nextInt(888888)+111111;
//        logger.info
        return checkNum;
        }
}
