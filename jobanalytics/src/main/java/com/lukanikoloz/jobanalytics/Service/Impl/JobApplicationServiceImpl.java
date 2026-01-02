package com.lukanikoloz.jobanalytics.Service.Impl;

import com.lukanikoloz.jobanalytics.Repository.CountryRepository;
import com.lukanikoloz.jobanalytics.Repository.CvVersionRepository;
import com.lukanikoloz.jobanalytics.Repository.JobApplicationRepository;
import com.lukanikoloz.jobanalytics.Repository.PlatformRepository;
import com.lukanikoloz.jobanalytics.Service.JobApplicationService;
import com.lukanikoloz.jobanalytics.domain.Entity.Country;
import com.lukanikoloz.jobanalytics.domain.Entity.CvVersion;
import com.lukanikoloz.jobanalytics.domain.Entity.JobApplication;
import com.lukanikoloz.jobanalytics.domain.Entity.Platform;
import com.lukanikoloz.jobanalytics.domain.Request.CreateJobCreateRequest;
import com.lukanikoloz.jobanalytics.domain.Response.JobApplicationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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

        JobApplication job = new JobApplication(
                cvVersion,
                request.companyName(),
                request.companySize(),
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

