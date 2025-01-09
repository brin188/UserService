package com.example.userservice.services;

import com.example.userservice.models.User;
import com.example.userservice.models.Token;
import com.example.userservice.repos.TokenRepo;
import com.example.userservice.repos.UserRepo;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private TokenRepo tokenRepo;
    private UserRepo userRepo;
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, BCryptPasswordEncoder passwordEncoder, TokenRepo tokenRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepo = tokenRepo;
    }

    public User signUp(String name, String email, String password) {
        //Add any validation for email
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setHashedPassword(passwordEncoder.encode(password));
        return userRepo.save(user);
    }

    public Token login(String email, String password) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User with Email " +  email + " not found");
        }
        User user = optionalUser.get();
        if (!passwordEncoder.matches(password, user.getHashedPassword())) {
            throw new UsernameNotFoundException("User Email and password are not matching");
        }
        Token token = generateToken(user);
        return tokenRepo.save(token);
    }

    private Token generateToken(User user) {
        Token token = new Token();
        token.setUser(user);
        token.setValue(RandomStringUtils.randomAlphanumeric(10));
        token.setExpiryAt(System.currentTimeMillis() + 3600000L);
        return token;
    }

    public User validateToken(String token) {
        /* Token is valid if
         * 1. Token exists in DB
         * 2. Token has not expired
         * 3. Token is not marked as deleted */

        Optional<Token> optionalToken = tokenRepo.findByValueAndDeletedAndExpiryAtGreaterThan(
                token, false, System.currentTimeMillis());
        if (optionalToken.isEmpty()) {
            return null;
        }
        return optionalToken.get().getUser();
    }
}
