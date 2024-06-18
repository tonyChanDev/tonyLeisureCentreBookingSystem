package com.tonyleisurecentre.bookingsystem.models;

import com.tonyleisurecentre.bookingsystem.enums.SectionStatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name="section")
public class Section {
    @Id
    @Column(name="section_id")
    private String sectionId;

    @Column(name="facility")
    private String facility;

    @Column(name="start_date_time")
    private LocalDateTime startDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private SectionStatusEnum status;

    @Column(name="user_id")
    private Integer userId;
}
