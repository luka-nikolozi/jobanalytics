package com.lukanikoloz.jobanalytics.Service.Impl;

import com.lukanikoloz.jobanalytics.Repository.CountryRepository;
import com.lukanikoloz.jobanalytics.Repository.CvVersionRepository;
import com.lukanikoloz.jobanalytics.Repository.JobApplicationRepository;
import com.lukanikoloz.jobanalytics.Repository.PlatformRepository;
import com.lukanikoloz.jobanalytics.Service.JobApplicationService;
import com.lukanikoloz.jobanalytics.domain.Entity.*;
import com.lukanikoloz.jobanalytics.domain.Request.CreateJobCreateRequest;
import com.lukanikoloz.jobanalytics.domain.Request.UpdateJobApplicationRequest;
import com.lukanikoloz.jobanalytics.domain.Response.JobApplicationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final CvVersionRepository cvVersionRepository;
    private final PlatformRepository platformRepository;
    private final CountryRepository countryRepository;

    public JobApplicationServiceImpl(
            JobApplicationRepository jobApplicationRepository,
            CvVersionRepository cvVersionRepository,
            PlatformRepository platformRepository,
            CountryRepository countryRepository
    ) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.cvVersionRepository = cvVersionRepository;
        this.platformRepository = platformRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public JobApplicationResponse getById(Long id) {
        JobApplication job = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Job application not found: id=" + id
                ));

        return toResponse(job);
    }

    @Override
    public JobApplicationResponse getByCompanyName(String jobName) {
        JobApplication jobApplication = jobApplicationRepository.findJobApplicationByCompanyName(jobName);
        return toResponse(jobApplication);
    }


    @Override
    public void create(CreateJobCreateRequest request) {

        JobApplication jobApplications = jobApplicationRepository.findJobApplicationByCompanyName(request.companyName());
        if (jobApplications != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Job application already exists");
        }

        CvVersion cvVersion = cvVersionRepository.findById(request.cvVersionId())
                .orElseThrow(() -> new IllegalArgumentException("CV version not found"));

        Platform platform = null;
        if (request.platformId() != null) {
            platform = platformRepository.findById(request.platformId())
                    .orElseThrow(() -> new IllegalArgumentException("Platform not found"));
        }

        Country country = countryRepository.findById(request.countryId())
                .orElseThrow(() -> new IllegalArgumentException("Country not found"));

        validateCompanySize(request.companySize());

        JobApplication job = new JobApplication(
                cvVersion,
                request.companyName(),
                request.companySize().toUpperCase(),
                request.hasReferral(),
                platform,
                country,
                request.field(),
                request.hrContacted(),
                request.hrInterview(),
                request.homeProject(),
                request.techInterview(),
                "A",
                request.note()
        );

        jobApplicationRepository.save(job);

    }

    @Transactional
    @Override
    public JobApplicationResponse update(Long id, UpdateJobApplicationRequest request) {
        JobApplication job = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("JobApplication not found"));

        // simple fields
        if (request.companyName() != null) job.setCompanyName(request.companyName());
        if(request.companySize() != null){
            validateCompanySize(request.companySize());
            job.setCompanySize(request.companySize().toUpperCase());
        }
        if (request.hasReferral() != null) job.setHasReferral(request.hasReferral());

        if (request.field() != null) job.setField(request.field());
        if (request.hrContacted() != null) job.setHrContacted(request.hrContacted());
        if (request.hrInterview() != null) job.setHrInterview(request.hrInterview());
        if (request.homeProject() != null) job.setHomeProject(request.homeProject());
        if (request.techInterview() != null) job.setTechInterview(request.techInterview());

        if (request.note() != null) job.setNote(request.note());
        if (request.status() != null) job.setStatus(request.status());

        // relations by id (only if provided)
        if (request.cvVersionId() != null) {
            CvVersion cv = cvVersionRepository.findById(request.cvVersionId())
                    .orElseThrow(() -> new IllegalArgumentException("CV version not found"));
            job.setCvVersion(cv);
        }

        // allow platform to be set OR removed
        if (request.platformId() != null) {
            Platform platform = platformRepository.findById(request.platformId())
                    .orElseThrow(() -> new IllegalArgumentException("Platform not found"));
            job.setPlatform(platform);
        }

        if (request.countryId() != null) {
            Country country = countryRepository.findById(request.countryId())
                    .orElseThrow(() -> new IllegalArgumentException("Country not found"));
            job.setCountry(country);
        }

        JobApplication saved = jobApplicationRepository.save(job);

        return toResponse(saved);
    }

    private void validateCompanySize(String companySize) {
        if (companySize == null) {
            throw new IllegalArgumentException("companySize is required");
        }

        try {
            CompanySize.valueOf(companySize.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "companySize must be one of: S, M, L"
            );
        }
    }


    private static JobApplicationResponse toResponse(JobApplication job) {
        if(job == null){
            return null;
        }

        var cv = job.getCvVersion();
        var platform = job.getPlatform();
        var country = job.getCountry();

        return new JobApplicationResponse(
                /*job.getId(),*/
                job.getCompanyName(),
                job.getCompanySize(),
                job.isHasReferral(),

                /*cv.getId(),*/
                cv.getVersion(),

                /*platform != null ? platform.getId() : null,*/
                platform != null ? platform.getName() : null,

                /*country.getId(),*/
                country.getName(),
                /*country.getCode(),*/
                country.getRegion(),

                job.getField(),
                job.getHrContacted(),
                job.getHrInterview(),
                job.getHomeProject(),
                job.getTechInterview(),
                job.getNote(),
                job.getStatus(),
                job.getCreatedAt()
        );
    }

}

