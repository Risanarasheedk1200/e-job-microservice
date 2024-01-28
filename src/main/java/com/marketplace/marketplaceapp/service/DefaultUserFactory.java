package com.marketplace.marketplaceapp.service;

import org.springframework.stereotype.Component;

import com.marketplace.marketplaceapp.models.User;

@Component
public class DefaultUserFactory implements UserFactory {
    @Override
    public User createUser( String name,String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        return user;
    }
}
