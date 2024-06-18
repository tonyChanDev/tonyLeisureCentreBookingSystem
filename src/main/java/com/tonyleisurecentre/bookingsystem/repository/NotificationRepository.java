package com.tonyleisurecentre.bookingsystem.repository;

import com.tonyleisurecentre.bookingsystem.models.Notification;
import com.tonyleisurecentre.bookingsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUserEmail(String userEmail);
}
