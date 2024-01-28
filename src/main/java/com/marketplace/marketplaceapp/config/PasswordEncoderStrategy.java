package com.marketplace.marketplaceapp.config;

public interface PasswordEncoderStrategy {
    String encode(String password);
    boolean matches(String rawPassword, String encodedPassword);
}
