package project.shop1.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import project.shop1.entity.UserEntity;
import project.shop1.feature.join.repository.JoinRepository;

@RequiredArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {

    private final JoinRepository joinRepository;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        String account = (String) session.getAttribute("account");
        if (account == null) {
            return false;
        }
        UserEntity userEntity = joinRepository.findUserEntityByAccount(account).get();
        if (userEntity.getAdminCk()==0) { //로그인 상태가 아니거나 관리자 계정 아닌 경우
            return false;
        } else {
            return true; //관리자 계정인 경우
        }
    }
}
