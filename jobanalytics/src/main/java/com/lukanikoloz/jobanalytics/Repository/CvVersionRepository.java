package com.lukanikoloz.jobanalytics.Repository;

import com.lukanikoloz.jobanalytics.domain.Entity.CvVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvVersionRepository extends JpaRepository<CvVersion, Long> {
}
