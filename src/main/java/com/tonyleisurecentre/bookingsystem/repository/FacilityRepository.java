package com.tonyleisurecentre.bookingsystem.repository;

import com.tonyleisurecentre.bookingsystem.models.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, String> {
    //Optional findByValue(String value);
}
