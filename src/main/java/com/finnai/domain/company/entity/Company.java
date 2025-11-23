package com.finnai.domain.company.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String corpCode;
    private String name;
    private String stockCode;

    private String sector;
    private String market;

    private String homepageUrl;
    private String headquartersAddr;

    private LocalDateTime foundedDate;

    private String corporateRegNo;
    private String businessRegNo;
    private String phoneNumber;

    @Column(columnDefinition = "TEXT")
    private String overview;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
