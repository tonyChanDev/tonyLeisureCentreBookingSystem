package com.tonyleisurecentre.bookingsystem.mapper;

import com.tonyleisurecentre.bookingsystem.DTO.SectionPublicDto;
import com.tonyleisurecentre.bookingsystem.models.Section;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SectionMapper {
    List<SectionPublicDto> mapSectionToSectionPublic(List<Section> sections);

}
