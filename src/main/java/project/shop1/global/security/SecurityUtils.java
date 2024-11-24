package project.shop1.global.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import project.shop1.global.exception.BusinessException;
import project.shop1.global.exception.ErrorCode;

public class SecurityUtils {

    public static String getCurrentUsername(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "[ERROR] 인증 정보가 없습니다.");
        }
        return authentication.getName(); // name == account
    }
}
