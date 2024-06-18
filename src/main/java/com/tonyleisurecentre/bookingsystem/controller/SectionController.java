package com.tonyleisurecentre.bookingsystem.controller;

import com.tonyleisurecentre.bookingsystem.DTO.SectionPublicDto;
import com.tonyleisurecentre.bookingsystem.customvalidation.DateConstraint;
import com.tonyleisurecentre.bookingsystem.exception.BaseException;
import com.tonyleisurecentre.bookingsystem.exception.NotFoundException;
import com.tonyleisurecentre.bookingsystem.models.Section;
import com.tonyleisurecentre.bookingsystem.response.GenericResponse;
import com.tonyleisurecentre.bookingsystem.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public/section")
public class SectionController {

    private SectionService sectionService;

    @Autowired
    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping("/{date}")
    public GenericResponse<List<SectionPublicDto>> getSectionsByDate(@Validated @DateConstraint @PathVariable String date)
            throws NotFoundException {
        System.out.println("AdminSectionController::getSectionsByDate");
        return new GenericResponse<>(sectionService.getSections(date));
    }

}
