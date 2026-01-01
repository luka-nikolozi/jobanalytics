package com.lukanikoloz.jobanalytics.Repository;

import com.lukanikoloz.jobanalytics.domain.Entity.Platform;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformRepository extends JpaRepository<Platform, Long> {
}
