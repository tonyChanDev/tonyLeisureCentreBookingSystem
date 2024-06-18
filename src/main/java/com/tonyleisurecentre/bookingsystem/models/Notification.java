package com.tonyleisurecentre.bookingsystem.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="msg_id")
    private Integer msgId;

    @Column(name="user_email")
    private String userEmail;

    @Column(name="message")
    private String message;
}
