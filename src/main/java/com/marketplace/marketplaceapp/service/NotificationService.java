package com.marketplace.marketplaceapp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketplace.marketplaceapp.models.Notification;
import com.marketplace.marketplaceapp.repository.NotificationInterface;

@Service
public class NotificationService {

    private final NotificationInterface notificationRepository;

    @Autowired
    public NotificationService(NotificationInterface notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void addNotification(String userId, String message) {
        if (userId != null && !userId.isEmpty()) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage(message);
        notification.setTimestamp(new Date());
        notificationRepository.save(notification);
        }
    }

    public List<Notification> getNotificationsByUserIdAndRead(String userId, boolean read) {
        return notificationRepository.findByUserIdAndRead(userId, read);
    }

   
}
