package com.example.userservice.controllers;

import com.example.userservice.dtos.LoginRequestDto;
import com.example.userservice.dtos.SignUpRequestDto;
import com.example.userservice.dtos.TokenResponseDto;
import com.example.userservice.dtos.UserResponseDto;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserResponseDto signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        User user = userService.signUp(signUpRequestDto.getName(), signUpRequestDto.getEmail(),
                signUpRequestDto.getPassword());
        return UserResponseDto.fromUser(user);
    }

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        Token token = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        return TokenResponseDto.fromToken(token);
    }

    @GetMapping("/validate/{token}")
    public UserResponseDto validateToken(@PathVariable String token) {
        User user = userService.validateToken(token);
        return UserResponseDto.fromUser(user);
    }
}
