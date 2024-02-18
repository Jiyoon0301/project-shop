package project.shop1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import project.shop1.util.MailService;

@SpringBootApplication
public class Shop1Application {

	public static void main(String[] args) {
		SpringApplication.run(Shop1Application.class, args);
	}


//	@Bean
//	public MailService mailService(JavaMailSender javaMailSender) {
//		return new MailService(javaMailSender);
//	}
//
//	@Bean
//	public JavaMailSender javaMailSender() {
//		return new JavaMailSenderImpl(); // 실제 설정은 여기에 추가되어야 함
//	}
}
