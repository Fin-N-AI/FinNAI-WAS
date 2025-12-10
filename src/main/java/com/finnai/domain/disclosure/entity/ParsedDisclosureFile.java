package com.finnai.domain.disclosure.entity;

import com.finnai.domain.company.entity.Company;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "parsed_disclosure_file")
public class ParsedDisclosureFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disclosure_file_id", nullable = false)
    private DisclosureFile disclosureFile;
    @Column(columnDefinition = "TEXT")
    private String companyOverview;

    @Column(columnDefinition = "TEXT")
    private String businessContents;
    @Column(columnDefinition = "TEXT")
    private String shareholderInfo;
    @Column(columnDefinition = "TEXT")
    private String investorProtection;
    @Column(columnDefinition = "TEXT")
    private String contingentLiabilities;

}