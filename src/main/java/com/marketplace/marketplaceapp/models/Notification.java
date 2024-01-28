package com.marketplace.marketplaceapp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

import java.util.Date;

@Data
@Document(collection = "notifications")
public class Notification {
    @Id
    private String id;

    private String userId; 
    private String message;
    private Date timestamp;
    private boolean read=false;

}
