package project.shop1.domain.auth.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import project.shop1.domain.auth.dto.LoginResponseDto;
import project.shop1.domain.auth.dto.SocialLoginRequestDto;
import project.shop1.domain.auth.naver.dto.NaverUserInfoResponseDto;
import project.shop1.domain.auth.naver.dto.NaverUserLoginResponseDto;
import project.shop1.domain.user.entity.UserEntity;
import project.shop1.domain.user.enums.UserRank;
import project.shop1.domain.user.repository.UserRepository;
import project.shop1.global.exception.BusinessException;
import project.shop1.global.security.jwt.JwtTokenProvider;
import project.shop1.global.security.jwt.dto.JwtToken;
import project.shop1.global.security.redis.repository.RefreshTokenRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class) //Junit5
public class NaverLoginServiceImplTest {
    @Spy // NaverLoginServiceImpl의 일부 메서드를 mocking
    @InjectMocks
    private NaverLoginServiceImpl naverLoginService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

//    @Test
//    void 네이버_로그인_성공_테스트() {
//        // Given
//        SocialLoginRequestDto socialLoginRequestDto = new SocialLoginRequestDto("mock-code");
//
//        // Mock Naver Access Token Response
//        String mockAccessToken = "mock-access-token";
//        NaverUserInfoResponseDto.ResponseData userInfo = new NaverUserInfoResponseDto.ResponseData(
//                "mock-id",
//                "mock-email@test.com",
//                "mock-user"
//        );
//        NaverUserInfoResponseDto mockUserInfoResponseDto = new NaverUserInfoResponseDto(
//                "success",
//                "OK",
//                userInfo
//        );
//
//        NaverUserLoginResponseDto mockLoginResponse = new NaverUserLoginResponseDto(
//                "mock-email@test.com",
//                "mock-id"
//        );
//
//        JwtToken mockJwtToken = new JwtToken("Bearer", "mock-access-token", "mock-refresh-token");
//        Authentication mockAuthentication = mock(Authentication.class);
//
//        // Mocking 의존성 동작
//        doReturn(mockAccessToken).when(naverLoginService).requestNaverAccessToken(socialLoginRequestDto);
//        doReturn(mockUserInfoResponseDto).when(naverLoginService).requestNaverUserInfo(mockAccessToken);
//        doReturn(mockLoginResponse).when(naverLoginService).naverUserLogin(mockUserInfoResponseDto);
//
//        when(authenticationManager.authenticate(any())).thenReturn(mockAuthentication);
//        when(jwtTokenProvider.generateToken(any())).thenReturn(mockJwtToken);
//        when(userRepository.findByEmail("mock-email@test.com"))
//                .thenReturn(Optional.of(UserEntity.builder()
//                        .email("mock-email@test.com")
//                        .name("mock-user")
//                        .userRank(UserRank.MEMBER)
//                        .build()));
//
//        // When
//        LoginResponseDto result = naverLoginService.login(socialLoginRequestDto);
//
//        // Then
//        assertThat(result).isNotNull();
//        assertThat(result.getAccessToken()).isEqualTo("mock-access-token");
//        assertThat(result.getRefreshToken()).isEqualTo("mock-refresh-token");
//        assertThat(result.getEmail()).isEqualTo("mock-email@test.com");
//
//        // Verify 호출 여부
//        verify(userRepository, times(1)).findByEmail("mock-email@test.com");
//        verify(jwtTokenProvider, times(1)).generateToken(any());
//        verify(refreshTokenRepository, times(1)).save(any());
//    }

//    @Test
//    void 네이버_로그인_성공() {
//        // Given
//        SocialLoginRequestDto socialLoginRequestDto = new SocialLoginRequestDto("mock-code");
//
//        String mockAccessToken = "mock-access-token";
//        NaverUserInfoResponseDto.ResponseData mockUserInfo = new NaverUserInfoResponseDto.ResponseData("mock-id", "mock-email@test.com", "mock-user");
//        NaverUserInfoResponseDto mockUserInfoResponseDto = new NaverUserInfoResponseDto("mock-result-code", "mock-message", mockUserInfo);
//
//        when(userRepository.findByEmail("mock-email@test.com"))
//                .thenReturn(Optional.of(UserEntity.builder().email("mock-email@test.com").build()));
//        when(jwtTokenProvider.generateToken(any())).thenReturn(new JwtToken("Bearer", "mock-access-token", "mock-refresh-token"));
//
//        // Stub `requestNaverAccessToken` and `requestNaverUserInfo` via partial mocking
//        NaverLoginServiceImpl spyService = spy(naverLoginService);
//        doReturn(mockAccessToken).when(spyService).requestNaverAccessToken(socialLoginRequestDto);
//        doReturn(mockUserInfoResponseDto).when(spyService).requestNaverUserInfo(mockAccessToken);
//
////        when(authenticationManagerBuilder.auth)
//
//        // When
//        LoginResponseDto result = spyService.login(socialLoginRequestDto);
//
//        // Then
//        assertThat(result).isNotNull();
//        assertThat(result.getAccessToken()).isEqualTo("mock-access-token");
//        assertThat(result.getRefreshToken()).isEqualTo("mock-refresh-token");
//        assertThat(result.getEmail()).isEqualTo("mock-email@test.com");
//    }

    @Test
    void 존재하는_사용자가_없을_때_예외_발생() {
        // Given
        SocialLoginRequestDto socialLoginRequestDto = new SocialLoginRequestDto("mock-code");

        String mockAccessToken = "mock-access-token";
        NaverUserInfoResponseDto.ResponseData mockUserInfo = new NaverUserInfoResponseDto.ResponseData("mock-id", "notfound-email@test.com", "Mock User");
        NaverUserInfoResponseDto mockUserInfoResponseDto = new NaverUserInfoResponseDto("mock-result-code", "mock-message", mockUserInfo);

        when(userRepository.findByEmail("notfound-email@test.com")).thenReturn(Optional.empty());

        // Stub `requestNaverAccessToken` and `requestNaverUserInfo` via partial mocking
        NaverLoginServiceImpl spyService = spy(naverLoginService);
        doReturn(mockAccessToken).when(spyService).requestNaverAccessToken(socialLoginRequestDto);
        doReturn(mockUserInfoResponseDto).when(spyService).requestNaverUserInfo(mockAccessToken);

        // When / Then
        assertThatThrownBy(() -> spyService.login(socialLoginRequestDto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("사용자를 찾을 수 없습니다.");
    }
}
