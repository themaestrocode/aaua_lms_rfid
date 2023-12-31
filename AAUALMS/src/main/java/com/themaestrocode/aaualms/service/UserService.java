package com.themaestrocode.aaualms.service;

import com.themaestrocode.aaualms.repository.UserRepository;

public class UserService {

    public boolean findUser(String userId) {
        UserRepository userRepository = new UserRepository();
        return userRepository.findUser(userId);
    }
}
