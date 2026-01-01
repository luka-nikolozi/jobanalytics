package com.lukanikoloz.jobanalytics.Service;

import com.lukanikoloz.jobanalytics.domain.Entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
}
