package com.marketplace.marketplaceapp.repository;

import java.util.List;

import com.marketplace.marketplaceapp.models.Notification;

public interface NotificationInterface {

    List<Notification> findByUserIdAndRead(String userId, boolean read);

    <S extends Notification> S save(S entity);

}