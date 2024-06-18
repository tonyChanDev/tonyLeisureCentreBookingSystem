package com.tonyleisurecentre.bookingsystem.service;

import com.tonyleisurecentre.bookingsystem.DTO.SectionIdDto;
import com.tonyleisurecentre.bookingsystem.DTO.SectionPublicDto;
import com.tonyleisurecentre.bookingsystem.Util.DateTimeUtil;
import com.tonyleisurecentre.bookingsystem.constant.SectionConstant;
import com.tonyleisurecentre.bookingsystem.enums.FacilityStatusEnum;
import com.tonyleisurecentre.bookingsystem.enums.SectionStatusEnum;
import com.tonyleisurecentre.bookingsystem.exception.BaseException;
import com.tonyleisurecentre.bookingsystem.exception.NotFoundException;
import com.tonyleisurecentre.bookingsystem.exception.SectionAlreadyExistedException;
import com.tonyleisurecentre.bookingsystem.mapper.SectionMapper;
import com.tonyleisurecentre.bookingsystem.models.Facility;
import com.tonyleisurecentre.bookingsystem.models.Section;
import com.tonyleisurecentre.bookingsystem.models.User;
import com.tonyleisurecentre.bookingsystem.repository.SectionRepository;
import com.tonyleisurecentre.bookingsystem.response.ErrMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * SectionService is the logics:
 * Let public fetch the sections
 * Let admin create the sections
 * Let the member register the sections
 */

@Service
public class SectionService {

    private NotificationService notificationService;

    private SectionRepository sectionRepository;

    private SectionMapper sectionMapper;

    private UserService userService;

    @Autowired
    public SectionService(SectionRepository sectionRepository,
                          SectionMapper sectionMapper,
                          NotificationService notificationService,
                          UserService userService) {
        this.sectionRepository = sectionRepository;
        this.sectionMapper = sectionMapper;
        this.notificationService = notificationService;
        this.userService = userService;
    }

    //For public to fetch the section data by date
    public List<SectionPublicDto> getSections(String dateStr) throws NotFoundException {
        System.out.println("SectionService::getSections::dateStr: ".concat(dateStr));
        LocalDate date = DateTimeUtil.strToLocalDate(dateStr);
        LocalDateTime startDateTime = LocalDateTime.of(date,LocalTime.of(0,0,0));
        LocalDateTime endDateTime = LocalDateTime.of(date,LocalTime.of(23,59,59));
        List<Section> sections = sectionRepository.findSectionByDateTime(startDateTime, endDateTime);
        if (sections.isEmpty()) {
            throw new NotFoundException("There are no sections on ".concat(dateStr));
        }
        List<SectionPublicDto> sectionPublicDtos = sectionMapper.mapSectionToSectionPublic(sections);
        return sectionPublicDtos;
    }

    public List<Section> getSectionByUserId() throws NotFoundException {
        Integer userId = getUserId();
        List<Section> sections = sectionRepository.findOccupiedSectionByUserId(userId);
        return sections;
    }

    private Integer getUserId() throws NotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer userId = null;
        if (principal instanceof User) {
            var user = (User) principal;
            System.out.println("SectionService::getUserId: ".concat(user.getId().toString()));
            userId = user.getId();
        }
        if (userId == null) {
            throw new NotFoundException("The user is not found");
        }
        return userId;
    }

    /**
     * For the member to register the section by the section id
     * @param SectionId
     * @return the section which the user registered
     * Throw exception when the user check or section check is failed
     * @throws BaseException
     */
    public Section registerSection(String SectionId) throws BaseException {
        System.out.println("SectionService::registerSection::SectionId: ".concat(SectionId));
        //Get the userId by token
        Integer userId = getUserId();
        checkUserAvailability(userId);
        //Get the section by sectionId
        Optional<Section> sections = sectionRepository.findById(SectionId);
        if(sections.isEmpty()) {
            throw new NotFoundException("The section cannot not be found");
        }
        Section sectionForRegister = sections.stream().findFirst().get();
        checkSectionAvailability(sectionForRegister);
        //Update the section Status and the userId field
        sectionForRegister.setStatus(SectionStatusEnum.OCCUPIED);
        sectionForRegister.setUserId(userId);

        sectionRepository.save(sectionForRegister);
        return sectionForRegister;
    }

    /**
     * Checking the user availability before user register the section
     * When the user register 2 or more sections,
     * the system will not allow the user register one more
     */
    private void checkUserAvailability(Integer userId) throws BaseException {
        List<Section> sectionForUser = sectionRepository.findOccupiedSectionByUserId(userId);
        if (sectionForUser.size() >=2) {
            var errMsg = new ErrMsg();
            errMsg.setCode("60450");
            errMsg.setDescription("You are not allow to register another section");
            var ex = new BaseException();
            ex.setErrorMessages(List.of(errMsg));
            throw ex;
        }
    }

    /**
     * Checking the section availability before registration
     * check the section status is available or not
     */
    private void checkSectionAvailability(Section section) throws BaseException {
        if(!section.getStatus().equals(SectionStatusEnum.AVAILABLE)) {
            var errMsg = new ErrMsg();
            errMsg.setCode("60900");
            errMsg.setDescription("The section is not available.");
            throw new BaseException(List.of(errMsg));
        }
    }

    /**
     * To check the provided date is already existed in DB or not
     * @param date
     * If the sections of that date is existed then throw
     * @throws SectionAlreadyExistedException
     */
    private void checkDateAvailability(LocalDate date) throws SectionAlreadyExistedException {
        LocalDateTime startDateTime = LocalDateTime.of(date,LocalTime.of(0,0,0));
        LocalDateTime endDateTime = LocalDateTime.of(date,LocalTime.of(23,59,59));
        if(!sectionRepository.findSectionByDateTime(startDateTime, endDateTime).isEmpty()) {
            throw new SectionAlreadyExistedException();
        }
    }

    //For Admin to create the sections
    public List<Section> createSection(String dateStr) throws SectionAlreadyExistedException {
        System.out.println("SectionService::createSection::dateStr: ".concat(dateStr));
        LocalDate date = DateTimeUtil.strToLocalDate(dateStr);
        checkDateAvailability(date);

        List<Section> sectionList = new ArrayList<>();

        //TODO get the facility constant from DB
        for(LocalTime time : SectionConstant.SECTION_START_TIME) {
            LocalDateTime dateTime = LocalDateTime.of(date, time);
            for (Facility facility : SectionConstant.FACILITIES) {
                if (!facility.getFacilityId().isBlank()
                        && facility.getStatus().equals(FacilityStatusEnum.AVAILABLE)) {
                    var facilityId = facility.getFacilityId();
                    String sectionId = DateTimeUtil.dateTimeToSectionId(dateTime, facilityId);
                    var section = new Section();
                    section.setSectionId(sectionId);
                    section.setStartDateTime(dateTime);
                    section.setFacility(facilityId);
                    section.setStatus(SectionStatusEnum.AVAILABLE);
                    sectionRepository.save(section);
                    sectionList.add(section);
                }
            }
        }
        return sectionList;
    }

    /**
     * For admin to cancel a section by sectionId
     * save a notification for member
     * @param sectionIdDto
     * @return
     */
    public Section cancelSection(SectionIdDto sectionIdDto) throws BaseException {
        //Find the section by section id
        var sections = sectionRepository.findById(sectionIdDto.getSectionId());
        //Throw the exception when the section does not exist
        if(sections.isEmpty()) {
            throw new NotFoundException("The section cannot not be found");
        }
        //Throw an ex when the section is cancelled
        var section = sections.stream().findFirst().get();
        if (section.getStatus().equals(SectionStatusEnum.CANCEL)){
            var errMsg = new ErrMsg();
            errMsg.setCode("60901");
            errMsg.setDescription("The section is already cancel.");
            throw new BaseException(List.of(errMsg));
        }
        //Update the status and userId of the section
        section.setStatus(SectionStatusEnum.CANCEL);
        var userId = section.getUserId();
        if(userId == null) {
            sectionRepository.save(section);
            return section;
        }
        section.setUserId(null);
        sectionRepository.save(section);
        //Create a notification for the user
        var user = userService.getUserById(userId, "The user attached to the section cannot be found.");
        String userEmail = user.getEmail();
        notificationService.createNotification(userEmail,
                sectionIdDto.getSectionId().concat(" is cancelled."));
        return section;
    }
}
