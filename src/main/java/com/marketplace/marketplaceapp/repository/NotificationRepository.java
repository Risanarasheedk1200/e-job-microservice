package com.marketplace.marketplaceapp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.marketplace.marketplaceapp.models.Notification;

public interface NotificationRepository extends MongoRepository<Notification, String>, NotificationInterface {
}

