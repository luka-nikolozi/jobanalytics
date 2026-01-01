package com.lukanikoloz.jobanalytics.domain.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cv_version")
@Getter
@Setter
public class CvVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String version;

    @Column(columnDefinition = "text")
    private String description;

    // getters & setters
}
