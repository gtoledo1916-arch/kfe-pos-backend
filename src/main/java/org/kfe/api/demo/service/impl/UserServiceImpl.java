package org.kfe.api.demo.service.impl;

import org.kfe.api.demo.entity.User;
import org.kfe.api.demo.repository.UserRepository;
import org.kfe.api.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> searchByName(String name) {
        return userRepository.findByUsernameContainingIgnoreCase(name);
    }
}
