package project.shop1.global.security.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.global.exception.BusinessException;
import project.shop1.global.exception.ErrorCode;
import project.shop1.global.security.redis.dto.RefreshToken;
import project.shop1.global.security.redis.repository.RefreshTokenRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void saveTokenInfo(String account, String refreshToken, String accessToken){
        refreshTokenRepository.save(new RefreshToken(account, refreshToken, accessToken));
    }

    @Transactional
    public void removeRefreshToken(String accessToken){
        refreshTokenRepository.findByAccessToken(accessToken)
                .ifPresent(refreshToken -> refreshTokenRepository.delete(refreshToken));
    }

    public RefreshToken findByAccount(String account) {
        Optional<RefreshToken> result = refreshTokenRepository.findById(account);
        if (result.isEmpty()){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "토큰이 존재하지 않습니다.");
        }
        return result.get();
    }
}
