package project.shop1.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.shop1.common.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
}
