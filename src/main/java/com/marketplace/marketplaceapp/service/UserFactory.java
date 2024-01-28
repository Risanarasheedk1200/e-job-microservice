package com.marketplace.marketplaceapp.service;


import com.marketplace.marketplaceapp.models.User;

public interface UserFactory {
    User createUser(String name,String email, String password);
}

