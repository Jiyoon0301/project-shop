package project.shop1.feature.login.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop1.feature.login.service.LoginService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginServiceImpl1 implements LoginService {
}
