package com.lukanikoloz.jobanalytics.Repository;

import com.lukanikoloz.jobanalytics.domain.Entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    JobApplication findJobApplicationByCompanyName(String companyName);
}
