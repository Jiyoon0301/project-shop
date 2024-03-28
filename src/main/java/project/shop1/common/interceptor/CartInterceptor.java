package project.shop1.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import project.shop1.entity.UserEntity;

public class CartInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{

        HttpSession session = request.getSession();
//        String account = (String) session.getAttribute("account");
        UserEntity userEntity = (UserEntity) session.getAttribute("userEntity");

        if (userEntity == null){
            return false; // controller로 넘어가지 않음
        } else {
            return true;
        }
    }
}
