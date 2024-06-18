package com.tonyleisurecentre.bookingsystem.controller.member;

import com.tonyleisurecentre.bookingsystem.DTO.SectionIdDto;
import com.tonyleisurecentre.bookingsystem.exception.BaseException;
import com.tonyleisurecentre.bookingsystem.exception.NotFoundException;
import com.tonyleisurecentre.bookingsystem.models.Section;
import com.tonyleisurecentre.bookingsystem.response.GenericResponse;
import com.tonyleisurecentre.bookingsystem.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member/section")
public class MemberSectionController {

    private SectionService sectionService;

    @Autowired
    public MemberSectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @PostMapping("/register")
    public GenericResponse<Section> registerSection(@RequestBody @Validated SectionIdDto sectionIdDto)
            throws BaseException {
        return new GenericResponse<>(sectionService.registerSection(sectionIdDto.getSectionId()));
    }

    @GetMapping("")
    public GenericResponse<List<Section>> getSectionsByUserId()
            throws BaseException {
        return new GenericResponse<>(sectionService.getSectionByUserId());
    }

}
