package com.lukanikoloz.jobanalytics.Repository;

import com.lukanikoloz.jobanalytics.domain.Entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}
