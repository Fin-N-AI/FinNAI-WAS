package com.finnai.domain.disclosure.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.finnai.domain.company.entity.Company;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "disclosure_list")
public class DisclosureList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false, unique = true)
    private String rceptNo;

    private String reportNm;

    private LocalDate rceptDt;

    private String rptType;

    private String flrNm;

    private LocalDateTime createdAt;
}
