package com.lukanikoloz.jobanalytics.Controller;

import com.lukanikoloz.jobanalytics.Service.UserService;
import com.lukanikoloz.jobanalytics.domain.Entity.User;
import com.lukanikoloz.jobanalytics.domain.Response.GetAllUsersResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() {
        List<User> users = userService.getAllUsers();
        return users;
    }

    /*@GetMapping
    public List<GetAllUsersResponse> getAll() {
        return userService.getAllUsers()
                .stream()
                .map(GetAllUsersResponse::fromEntity)
                .toList();
    }*/

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
