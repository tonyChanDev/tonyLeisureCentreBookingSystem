package com.tonyleisurecentre.bookingsystem.constant;

import com.tonyleisurecentre.bookingsystem.enums.FacilityEnum;
import com.tonyleisurecentre.bookingsystem.enums.FacilityStatusEnum;
import com.tonyleisurecentre.bookingsystem.models.Facility;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SectionConstant {
    public static final List<LocalTime> SECTION_START_TIME = new ArrayList<>();
    static {
        SECTION_START_TIME.add(LocalTime.of(10,0,0));
        SECTION_START_TIME.add(LocalTime.of(11,0,0));
        SECTION_START_TIME.add(LocalTime.of(12,0,0));
        SECTION_START_TIME.add(LocalTime.of(14,0,0));
        SECTION_START_TIME.add(LocalTime.of(15,0,0));
        SECTION_START_TIME.add(LocalTime.of(16,0,0));
    }

    public static final List<Facility> FACILITIES = new ArrayList<>();
    static {
        FACILITIES.add(new Facility("BK1", FacilityEnum.BASKETBALL, FacilityStatusEnum.AVAILABLE));
        FACILITIES.add(new Facility("BM1", FacilityEnum.BADMINTON, FacilityStatusEnum.AVAILABLE));
        FACILITIES.add(new Facility("BM2", FacilityEnum.BADMINTON, FacilityStatusEnum.AVAILABLE));
        FACILITIES.add(new Facility("TN1", FacilityEnum.TENNIS, FacilityStatusEnum.AVAILABLE));
        FACILITIES.add(new Facility("TN2", FacilityEnum.TENNIS, FacilityStatusEnum.AVAILABLE));
    }
}
