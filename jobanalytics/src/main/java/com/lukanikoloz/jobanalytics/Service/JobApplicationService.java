package com.lukanikoloz.jobanalytics.Service;

import com.lukanikoloz.jobanalytics.domain.Request.CreateJobCreateRequest;
import com.lukanikoloz.jobanalytics.domain.Response.JobApplicationResponse;

import java.util.List;

public interface JobApplicationService {
    JobApplicationResponse getById(Long id);

    List<JobApplicationResponse> getByCompanyName(String jobName);

    void create(CreateJobCreateRequest request);
}