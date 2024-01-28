package com.marketplace.marketplaceapp.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.marketplace.marketplaceapp.models.User;
import com.marketplace.marketplaceapp.service.UserService;
import com.marketplace.marketplaceapp.service.UserServiceImpl;
import org.springframework.dao.DuplicateKeyException;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserService userService;

     @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody User user) {
        try {
            User createdUser = userService.signup(user);
            return ResponseEntity.ok(createdUser);
        } catch (DuplicateKeyException e) {
            logger.info("Duplicate email - Signup controller");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists. Please use a different email or just login");
        } catch (RuntimeException e) {
            logger.error("Error during signup:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during signup.");
        }
    }

    @PostMapping("/login")
    public User login(@RequestParam String email, @RequestParam String password) {
        return userService.login(email, password);
    }

}
