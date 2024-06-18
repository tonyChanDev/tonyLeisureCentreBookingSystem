package com.tonyleisurecentre.bookingsystem.controller.admin;

import com.tonyleisurecentre.bookingsystem.DTO.SectionDateDto;
import com.tonyleisurecentre.bookingsystem.DTO.SectionIdDto;
import com.tonyleisurecentre.bookingsystem.exception.BaseException;
import com.tonyleisurecentre.bookingsystem.exception.SectionAlreadyExistedException;
import com.tonyleisurecentre.bookingsystem.models.Section;
import com.tonyleisurecentre.bookingsystem.response.GenericResponse;
import com.tonyleisurecentre.bookingsystem.service.SectionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The admin can create the section for user to book manually.
 * Just pass the date and the api will create the sections automatically.
 * Admin can just pass the section id and cancel the section.
 */

@RestController
@RequestMapping("/admin/section")
public class AdminSectionController {

    private SectionService sectionService;

    @Autowired
    public AdminSectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    //Create the section by the date
    @PostMapping("/create")
    public GenericResponse<List<Section>> createSections(@Valid @RequestBody SectionDateDto sectionDateDto)
            throws SectionAlreadyExistedException {
        System.out.println("AdminSectionController::createSection::sectionDate: ".concat(sectionDateDto.getSectionDate()));
        var response = new GenericResponse<>(sectionService.createSection(sectionDateDto.getSectionDate()));
        return response;
    }

    @PostMapping("/cancel")
    public GenericResponse<Section> cancelSection(@Valid @RequestBody SectionIdDto sectionIdDto)
            throws BaseException {
        System.out.println("AdminSectionController::cancelSection::sectionIdDto: ".concat(sectionIdDto.getSectionId()));
        return new GenericResponse<>(sectionService.cancelSection(sectionIdDto));
    }
}
