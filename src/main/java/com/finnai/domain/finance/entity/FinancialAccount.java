package com.finnai.domain.finance.entity;

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
@Table(name = "financial_account")
public class FinancialAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    private Integer bsnsYear;
    private String reprtCode;

    private String accountId;
    private String accountNm;

    private Long amount;

    private LocalDateTime createdAt;
}
