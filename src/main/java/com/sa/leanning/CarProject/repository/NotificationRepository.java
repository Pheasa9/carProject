package com.sa.leanning.CarProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sa.leanning.CarProject.Entities.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    long countByReadStatusFalse();
}