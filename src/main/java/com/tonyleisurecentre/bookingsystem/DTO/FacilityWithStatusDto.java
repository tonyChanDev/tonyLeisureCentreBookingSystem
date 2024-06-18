package com.tonyleisurecentre.bookingsystem.DTO;

import com.tonyleisurecentre.bookingsystem.enums.FacilityStatusEnum;
import lombok.Data;

@Data
public class FacilityWithStatusDto extends FacilityDto{
    private String status;
}
