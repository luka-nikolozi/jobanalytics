package com.lukanikoloz.jobanalytics.domain.Response;

import java.time.LocalDateTime;

public record JobApplicationResponse(
        /*Long id,*/
        String companyName,
        String companySize,
        boolean hasReferral,

        /*Long cvVersionId,*/
        String cvVersion,

        /*Long platformId,*/
        String platformName,

        /*Long countryId,*/
        String countryName,
        /*String countryCode,*/
        String region,

        String field,
        String hrContacted,
        String hrInterview,
        String homeProject,
        String techInterview,
        String note,
        String status,
        LocalDateTime createdAt
) {}

