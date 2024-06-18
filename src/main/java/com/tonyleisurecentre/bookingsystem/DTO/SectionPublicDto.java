package com.tonyleisurecentre.bookingsystem.DTO;

import com.tonyleisurecentre.bookingsystem.enums.SectionStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SectionPublicDto {

    private String sectionId;

    private String facility;

    private LocalDateTime startDateTime;

    private SectionStatusEnum status;
}
