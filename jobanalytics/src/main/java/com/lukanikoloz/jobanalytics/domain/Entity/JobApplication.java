package com.lukanikoloz.jobanalytics.domain.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_application")
@Getter
@Setter
@RequiredArgsConstructor
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cv_version_id", nullable = false)
    private CvVersion cvVersion;

    @Column(name = "company_name", nullable = false, unique = true)
    private String companyName;

    @Column(name = "company_size", nullable = false, length = 1)
    private String companySize;

    @Column(name = "has_referral", nullable = false)
    private boolean hasReferral = false;

    @ManyToOne
    @JoinColumn(name = "platform_id")
    private Platform platform;

    @ManyToOne(optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @Column(length = 255)
    private String field;

    @Column(length = 10)
    private String hrContacted;

    @Column(length = 10)
    private String hrInterview;

    @Column(length = 10)
    private String homeProject;

    @Column(length = 10)
    private String techInterview;

    @Column(columnDefinition = "text")
    private String note;

    @Column(length = 10)
    private String status = "A";

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public JobApplication(
            CvVersion cvVersion,
            String companyName,
            String companySize,
            boolean hasReferral,
            Platform platform,
            Country country,
            String field,
            String hrContacted,
            String hrInterview,
            String homeProject,
            String techInterview,
            String status,
            String note
    ) {
        this.cvVersion = cvVersion;
        this.companyName = companyName;
        this.companySize = companySize;
        this.hasReferral = hasReferral;
        this.platform = platform;
        this.country = country;
        this.field = field;
        this.hrContacted = hrContacted;
        this.hrInterview = hrInterview;
        this.homeProject = homeProject;
        this.techInterview = techInterview;
        this.status = status;
        this.note = note;
    }

}
