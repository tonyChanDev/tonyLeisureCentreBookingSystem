package com.tonyleisurecentre.bookingsystem.service;

import com.tonyleisurecentre.bookingsystem.DTO.NotificationDto;
import com.tonyleisurecentre.bookingsystem.exception.NotFoundException;
import com.tonyleisurecentre.bookingsystem.models.Notification;
import com.tonyleisurecentre.bookingsystem.models.User;
import com.tonyleisurecentre.bookingsystem.repository.NotificationRepository;
import com.tonyleisurecentre.bookingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private NotificationRepository notificationRepository;

    private UserRepository userRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository,
                               UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public void createNotification(String email, String msg) {
        var notice = new Notification();
        notice.setMessage(msg);
        notice.setUserEmail(email);
        //Save notification to DB
        notificationRepository.save(notice);
    }

    /**
     * Delete the notification by
     * @return
     * @throws NotFoundException
     */
    public String deleteNotification() throws NotFoundException {
        String userEmail = getUserEmail();
        List<Notification> notifications = notificationRepository.findByUserEmail(userEmail);
        if (notifications.isEmpty()) {
            throw new NotFoundException("The notifications cannot not be found");
        }
        notificationRepository.deleteAll(notifications);
        return "Delete the notification successfully";
    }

    /**
     * Get the notification for the member
     * @return
     * @throws NotFoundException
     */
    public List<Notification> getNotifications() throws NotFoundException {
        String userEmail = getUserEmail();
        List<Notification> notifications = notificationRepository.findByUserEmail(userEmail);
        return notifications;
    }

    private String getUserEmail() throws NotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = null;
        if (principal instanceof User) {
            var user = (User) principal;
            System.out.println("SectionService::getUserId: ".concat(user.getId().toString()));
            userEmail = user.getEmail();
        }
        if (userEmail == null) {
            throw new NotFoundException("The user email is not found");
        }
        return userEmail;
    }
}
