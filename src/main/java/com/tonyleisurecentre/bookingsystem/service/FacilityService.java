package com.tonyleisurecentre.bookingsystem.service;

import com.tonyleisurecentre.bookingsystem.DTO.FacilityDto;
import com.tonyleisurecentre.bookingsystem.DTO.FacilityWithStatusDto;
import com.tonyleisurecentre.bookingsystem.exception.NotFoundException;
import com.tonyleisurecentre.bookingsystem.exception.SaveException;
import com.tonyleisurecentre.bookingsystem.mapper.FacilityMapper;
import com.tonyleisurecentre.bookingsystem.models.Facility;
import com.tonyleisurecentre.bookingsystem.repository.FacilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacilityService {
    private FacilityMapper facilityMapper;

    private FacilityRepository facilityRepository;

    @Autowired
    public FacilityService(FacilityMapper facilityMapper, FacilityRepository facilityRepository) {
        this.facilityMapper = facilityMapper;
        this.facilityRepository = facilityRepository;
    }

    public List<FacilityWithStatusDto> getAllFacility() throws NotFoundException {
        List<Facility> facilities = facilityRepository.findAll();
        if (facilities.isEmpty()) {
            throw new NotFoundException("The facility records is empty");
        }
        return facilityMapper.facilitiesToFacilityWithStatusDtos(facilities);

    }

    public List<FacilityDto> createFacilities(List<FacilityDto> facilityDtos) throws SaveException {
        var facilities = facilityMapper.facilityDtoesToFacilities(facilityDtos);
        try {
            facilityRepository.saveAll(facilities);
        } catch(Exception ex) {
            throw new SaveException("fail to save to facility table");
        }
        return facilityDtos;
    }

    public FacilityWithStatusDto updateStatus(FacilityWithStatusDto facilityWithStatusDto) throws NotFoundException {
        Optional<Facility> facility = facilityRepository.findById(facilityWithStatusDto.getFacilityId());
        if(facility.isEmpty()) {
            throw new NotFoundException("The facility is not found");
        }
        Facility facility1 = facility.stream().findFirst().get();
        FacilityWithStatusDto facilityWithStatusDto1 = facilityMapper.facilityToFacilityWithStatusDto(facility1);

        return facilityWithStatusDto1;
    }

}
