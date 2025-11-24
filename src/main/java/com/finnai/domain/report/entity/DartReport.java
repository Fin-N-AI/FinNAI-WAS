package com.finnai.domain.report.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.finnai.domain.company.entity.Company;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dart_report")
public class DartReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(unique = true)
    private String rceptNo;

    private String reportType;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
}
