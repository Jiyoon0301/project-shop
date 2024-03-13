package project.shop1.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.shop1.common.repository.impl.UserRepositoryImpl;
import project.shop1.common.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;
}
