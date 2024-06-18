package com.tonyleisurecentre.bookingsystem.mapper;

import com.tonyleisurecentre.bookingsystem.DTO.FacilityDto;
import com.tonyleisurecentre.bookingsystem.DTO.FacilityWithStatusDto;
import com.tonyleisurecentre.bookingsystem.enums.FacilityEnum;
import com.tonyleisurecentre.bookingsystem.enums.FacilityStatusEnum;
import com.tonyleisurecentre.bookingsystem.models.Facility;
import org.mapstruct.*;

import java.util.List;
import java.util.Locale;

@Mapper(componentModel = "spring")
public interface FacilityMapper {

    @Mapping(source="type", target="type", qualifiedByName = "mapFacilityType")
    Facility facilityDtoToFacility(FacilityDto facilityDto);

    @AfterMapping
    default void afterMapFacilityToFacility(FacilityDto facilityDto, @MappingTarget Facility facility) {
        facility.setStatus(FacilityStatusEnum.AVAILABLE);
    }

    List<Facility> facilityDtoesToFacilities(List<FacilityDto> facilities);

    FacilityDto facilityToFacilityDto(Facility facilityDto);

    List<FacilityWithStatusDto> facilitiesToFacilityWithStatusDtos(List<Facility> facilities);

    @Mapping(source="status", target="status", qualifiedByName = "mapFacilityStatusStrToEnum")
    Facility facilityWithStatusDtoToFacility(FacilityWithStatusDto facilityWithStatusDto);

    @Mapping(source="status", target="status", qualifiedByName = "mapFacilityStatusEnumToStr")
    FacilityWithStatusDto facilityToFacilityWithStatusDto(Facility facility);

    @Named("mapFacilityStatusStrToEnum")
    default FacilityStatusEnum mapFacilityStatusStrToEnum(String facilityStatusStr) {
        return FacilityStatusEnum.valueOf(facilityStatusStr.toUpperCase(Locale.ROOT));
    }

    @Named("mapFacilityStatusEnumToStr")
    default String mapFacilityStatusEnumToStr(FacilityStatusEnum facilityStatusEnum) {
        return facilityStatusEnum.name();
    }

    @Named("mapFacilityType")
    default FacilityEnum mapFacilityType(String facilityType) {
        return FacilityEnum.valueOf(facilityType.toUpperCase(Locale.ROOT));
    }
}
