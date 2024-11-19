package project.shop1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@EnableRedisRepositories
//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) //커스터마이징 필요
@SpringBootApplication
public class Shop1Application {

	public static void main(String[] args) {
		SpringApplication.run(Shop1Application.class, args);
	}

	
}
