package com.tonyleisurecentre.bookingsystem.repository;

import com.tonyleisurecentre.bookingsystem.models.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, String> {

    @Query("select s from Section s where s.startDateTime >= :startDateTime AND s.startDateTime <= :endDateTime" )
    List<Section> findSectionByDateTime(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime);

    @Query("select s from Section s where s.userId = :userId AND s.status = SectionStatusEnum.OCCUPIED" )
    List<Section> findOccupiedSectionByUserId(Integer userId);

    List<Section> findByUserId(Integer userId);
}
