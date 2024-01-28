package com.marketplace.marketplaceapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.marketplace.marketplaceapp.config.PasswordEncoderStrategy;
import com.marketplace.marketplaceapp.models.User;
import com.marketplace.marketplaceapp.repository.UserRepository;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;


@Service
public class UserServiceImpl implements UserService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

     @Autowired
    private PasswordEncoderStrategy passwordEncoderStrategy; // Inject the PasswordEncoderStrategy
    
    @Autowired
    private UserFactory userFactory;

    @Override
    public User signup(User user) {
        // Check if the email already exists in the database
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.info("Duplicate email");
            // Handle the case where the email is already taken
            throw new DuplicateKeyException("Email already exists");
        }
        // Use the UserFactory to create and populate the User object
        User newUser = userFactory.createUser(user.getName(),user.getEmail(), user.getPassword());
        
        // Encode the password and save the user
        newUser.setPassword(passwordEncoderStrategy.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public User login(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent() && passwordEncoderStrategy.matches(password, optionalUser.get().getPassword())) {
            return optionalUser.get();
        }
        
        return null;
    }
  
    @Override
    public String getUserIdByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        
        // Check if the user is present in the Optional
        if (userOptional.isPresent()) {
            return userOptional.get().getId();
        } else {
            // Handle the case where the user is not found
            return null;
        }
    }
}

