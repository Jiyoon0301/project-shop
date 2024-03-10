package project.shop1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import project.shop1.util.MailService;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) //커스터마이징 필요...?
public class Shop1Application {

	public static void main(String[] args) {
		SpringApplication.run(Shop1Application.class, args);
	}

	
}
