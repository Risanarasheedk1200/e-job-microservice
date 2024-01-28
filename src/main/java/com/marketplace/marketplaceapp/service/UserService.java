package com.marketplace.marketplaceapp.service;

import com.marketplace.marketplaceapp.models.User;

public interface UserService {
    User signup(User user);
    User login(String email, String password);
    String getUserIdByEmail(String email);
}

