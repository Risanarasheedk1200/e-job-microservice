package com.marketplace.marketplaceapp.models;

import java.util.Optional;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Data;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;

    @Indexed(unique = true) 
    private String email;
    private String password;
    public Optional<String> map(Object object) {
    
        throw new UnsupportedOperationException("Unimplemented method 'map'");
    }


}

