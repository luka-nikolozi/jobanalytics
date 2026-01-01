package com.lukanikoloz.jobanalytics.Controller;

import com.lukanikoloz.jobanalytics.Service.JobApplicationService;
import com.lukanikoloz.jobanalytics.domain.Request.CreateJobCreateRequest;
import com.lukanikoloz.jobanalytics.domain.Response.JobApplicationResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-applications")
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService service) {
        this.jobApplicationService = service;
    }

    @GetMapping("/{id}")
    public JobApplicationResponse getById(@PathVariable Long id) {
        return jobApplicationService.getById(id);
    }

    @GetMapping("/by-company/{companyName}")
    public List<JobApplicationResponse> getJobByCompanyName(@PathVariable String companyName) {
        return jobApplicationService.getByCompanyName(companyName);
    }

    @PostMapping("/createNewJob")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody CreateJobCreateRequest request) {
        jobApplicationService.create(request);
    }


}

