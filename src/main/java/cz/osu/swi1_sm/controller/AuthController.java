package cz.osu.swi1_sm.controller;

import cz.osu.swi1_sm.model.dto.LoginRequest;
import cz.osu.swi1_sm.model.dto.UserToken;
import cz.osu.swi1_sm.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public UserToken login(@RequestBody LoginRequest request) {
        return userService.login(request.getEmail(), request.getPassword());
    }
}