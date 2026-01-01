package com.lukanikoloz.jobanalytics.Service.Impl;


import com.lukanikoloz.jobanalytics.Repository.UserRepository;
import com.lukanikoloz.jobanalytics.Service.UserService;
import com.lukanikoloz.jobanalytics.domain.Entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }
}


