package com.marketplace.marketplaceapp.models;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Data;

//Not using a builder pattern most of all fields here are not optional 
@Data
@Document(collection = "jobs")
public class Job {
    @Id
    private String id;
    private String title;
    private int numBids=0;     //no.of bids for the job
    private String description;
    private String jobRequirements;
    private String jobPosterName;
    private String jobContactInfo;
    private LocalDateTime expireAt=LocalDateTime.now().plusDays(3);
    private String leastBidAmount;
    private String jobBidderName;
    private String jobBidderContactInfo;

    @CreatedDate
    private LocalDateTime createdAt=LocalDateTime.now();

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private String status= "Active";
}


