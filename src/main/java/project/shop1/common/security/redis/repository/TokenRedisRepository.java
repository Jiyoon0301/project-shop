package project.shop1.common.security.redis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.shop1.entity.TokenRedis;

import java.util.Optional;

@Repository
public interface TokenRedisRepository extends CrudRepository<TokenRedis, String> {

    Optional<TokenRedis> findByAccessToken(String accessToken);

}
