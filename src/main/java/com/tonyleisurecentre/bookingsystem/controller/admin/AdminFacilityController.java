package com.tonyleisurecentre.bookingsystem.controller.admin;

import com.tonyleisurecentre.bookingsystem.DTO.FacilityDto;
import com.tonyleisurecentre.bookingsystem.DTO.FacilityWithStatusDto;
import com.tonyleisurecentre.bookingsystem.exception.NotFoundException;
import com.tonyleisurecentre.bookingsystem.exception.SaveException;
import com.tonyleisurecentre.bookingsystem.response.GenericResponse;
import com.tonyleisurecentre.bookingsystem.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/facility")
public class AdminFacilityController {

    private FacilityService facilityService;

    @Autowired
    public AdminFacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    //Getting the facility information
    @GetMapping
    public GenericResponse<List<FacilityWithStatusDto>> getFacility()
            throws NotFoundException {
        return new GenericResponse<>(facilityService.getAllFacility());
    }

    //Create the facility
    @PostMapping("/create")
    public GenericResponse<List<FacilityDto>> create(List<FacilityDto> facilityDtos)
            throws SaveException {
        return new GenericResponse<>(facilityService.createFacilities(facilityDtos));
    }

    //update the facility status
    @PostMapping("/update-status")
    public GenericResponse<FacilityWithStatusDto> updateStatus(FacilityWithStatusDto facility)
            throws NotFoundException {
        return new GenericResponse<>(facilityService.updateStatus(facility));
    }
}
