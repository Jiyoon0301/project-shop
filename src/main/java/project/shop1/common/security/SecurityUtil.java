package project.shop1.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import project.shop1.common.exception.BusinessException;
import project.shop1.common.exception.ErrorCode;

public class SecurityUtil {
    public static String getCurrentUsername(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "No authentication information.");
        }
        return authentication.getName(); // name == account
    }
}
