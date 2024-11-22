package project.shop1.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * EntityManager를 주입받고, 이를 사용하여 JPAQueryFactory를 생성하여 빈으로 등록하는 방법
 * 이렇게 하면 @Autowired 또는 생성자 주입을 통해 다른 클래스에서 사용할 수 있음
 */
@Configuration
public class QuerydslConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
