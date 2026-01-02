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

    @Transactional
    @Override
    public JobApplicationResponse update(Long id, UpdateJobApplicationRequest req) {
        JobApplication job = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("JobApplication not found"));

        // simple fields
        if (req.companyName() != null) job.setCompanyName(req.companyName());
        if (req.companySize() != null) job.setCompanySize(req.companySize());
        if (req.hasReferral() != null) job.setHasReferral(req.hasReferral());

        if (req.field() != null) job.setField(req.field());
        if (req.hrContacted() != null) job.setHrContacted(req.hrContacted());
        if (req.hrInterview() != null) job.setHrInterview(req.hrInterview());
        if (req.homeProject() != null) job.setHomeProject(req.homeProject());
        if (req.techInterview() != null) job.setTechInterview(req.techInterview());

        if (req.note() != null) job.setNote(req.note());
        if (req.status() != null) job.setStatus(req.status());

        // relations by id (only if provided)
        if (req.cvVersionId() != null) {
            CvVersion cv = cvVersionRepository.findById(req.cvVersionId())
                    .orElseThrow(() -> new IllegalArgumentException("CV version not found"));
            job.setCvVersion(cv);
        }

        // allow platform to be set OR removed
        if (req.platformId() != null) {
            Platform platform = platformRepository.findById(req.platformId())
                    .orElseThrow(() -> new IllegalArgumentException("Platform not found"));
            job.setPlatform(platform);
        }

        if (req.countryId() != null) {
            Country country = countryRepository.findById(req.countryId())
                    .orElseThrow(() -> new IllegalArgumentException("Country not found"));
            job.setCountry(country);
        }

        JobApplication saved = jobApplicationRepository.save(job);

        return toResponse(saved);
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

