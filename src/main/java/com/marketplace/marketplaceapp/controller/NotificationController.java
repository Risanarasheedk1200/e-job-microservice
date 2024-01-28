package com.marketplace.marketplaceapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.marketplace.marketplaceapp.models.Notification;
import com.marketplace.marketplaceapp.service.NotificationService;
import com.marketplace.marketplaceapp.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    @Autowired
    UserService userService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/email/{email}/read/{read}")
    public ResponseEntity<List<Notification>> getNotificationsByUserEmailAndRead(
            @PathVariable String email,
            @PathVariable boolean read
    ) {
        String userId = userService.getUserIdByEmail(email);

        if (userId!=null) {
            
            return ResponseEntity.ok(notificationService.getNotificationsByUserIdAndRead(userId, read));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
