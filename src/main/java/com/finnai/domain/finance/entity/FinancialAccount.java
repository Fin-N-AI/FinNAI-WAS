package com.finnai.domain.finance.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.finnai.domain.company.entity.Company;


/**
 * https://opendart.fss.or.kr/api/fnlttSinglAcntAll.json
 */
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

    // 공시 기준연도 (DART 재무제표 단일회사 API: bsns_year)
    private Integer bsnsYear;
    // 보고서 코드 (DART reprt_code, 예: 11011=사업보고서)
    private String reprtCode;

    // 계정 ID (DART account_id)
    private String accountId;
    // 계정명 (DART account_nm)
    private String accountNm;

    // 당기 금액 (DART thstrm_amount)
    @Column(name = "thstrm_amount")
    private Long thstrmAmount;

    private LocalDateTime createdAt;
}
