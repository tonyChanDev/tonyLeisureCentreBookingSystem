package com.tonyleisurecentre.bookingsystem.DTO;

import com.tonyleisurecentre.bookingsystem.customvalidation.DateConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class SectionDateDto {

    @NotBlank
    @DateConstraint
    public String sectionDate;
}
