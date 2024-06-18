package com.tonyleisurecentre.bookingsystem.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SectionIdDto {
    @NotBlank
    private String sectionId;
}
