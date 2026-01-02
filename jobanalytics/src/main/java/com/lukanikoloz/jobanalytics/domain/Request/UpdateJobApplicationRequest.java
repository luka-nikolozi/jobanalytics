package com.lukanikoloz.jobanalytics.domain.Request;

import jakarta.validation.constraints.NotBlank;

public record UpdateJobApplicationRequest(
        @NotBlank(message = "Company name is required")
        String companyName,

        String companySize,
        Boolean hasReferral,

        Long cvVersionId,
        Long platformId,
        Long countryId,

        String field,

        String hrContacted,
        String hrInterview,
        String homeProject,
        String techInterview,

        String note,
        String status
) {}