package com.tonyleisurecentre.bookingsystem.models;

import com.tonyleisurecentre.bookingsystem.enums.FacilityEnum;
import com.tonyleisurecentre.bookingsystem.enums.FacilityStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="facility")
public class Facility {
    @Id
    @Column(name="facility_id")
    private String facilityId;

    @Enumerated(EnumType.STRING)
    @Column(name="type")
    private FacilityEnum type;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private FacilityStatusEnum status;

}
