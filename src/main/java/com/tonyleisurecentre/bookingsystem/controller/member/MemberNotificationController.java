package com.tonyleisurecentre.bookingsystem.controller.member;

import com.tonyleisurecentre.bookingsystem.DTO.NotificationDto;
import com.tonyleisurecentre.bookingsystem.exception.NotFoundException;
import com.tonyleisurecentre.bookingsystem.models.Notification;
import com.tonyleisurecentre.bookingsystem.response.GenericResponse;
import com.tonyleisurecentre.bookingsystem.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/member/notification")
public class MemberNotificationController {

    private NotificationService notificationService;

    @Autowired
    public MemberNotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("")
    public GenericResponse<List<Notification>> getNotifications() throws NotFoundException {
        return new GenericResponse<>(notificationService.getNotifications());
    }

    @GetMapping("/read")
    public GenericResponse<String> deleteNotification() throws NotFoundException {
        return new GenericResponse<>(notificationService.deleteNotification());
    }
}
