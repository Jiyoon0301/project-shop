package project.shop1.domain.auth.service.common;

import lombok.Data;
import project.shop1.domain.user.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("google".equals(registrationId)) {
            return ofGoogle(userNameAttributeName, attributes);
        } else if ("kakao".equals(registrationId)) {
            return ofKakao(userNameAttributeName, attributes);
        } else if ("naver".equals(registrationId)) {
            return ofNaver(userNameAttributeName, attributes);
        }
        throw new IllegalArgumentException("Unsupported Registration Id");
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return new OAuthAttributes(attributes, userNameAttributeName,
                (String) attributes.get("name"),
                (String) attributes.get("email"));
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        return new OAuthAttributes(attributes, userNameAttributeName,
                (String) profile.get("nickname"),
                (String) kakaoAccount.get("email"));
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return new OAuthAttributes(attributes, userNameAttributeName,
                (String) response.get("name"),
                (String) response.get("email"));
    }

    public UserEntity toEntity() {
        return new UserEntity(name, email, "USER");
    }
}
