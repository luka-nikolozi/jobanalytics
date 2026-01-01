package com.lukanikoloz.jobanalytics.domain.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateJobCreateRequest(
        @NotBlank(message = "Company name is required")
        String companyName,

        @NotBlank(message = "Company size is required")
        String companySize,

        boolean hasReferral,

        @NotNull(message = "CV version is required")
        Long cvVersionId,

        Long platformId,

        @NotNull(message = "Country is required")
        Long countryId,

        String field,

        String hrContacted,
        String hrInterview,
        String homeProject,
        String techInterview,

        String note
) {}

