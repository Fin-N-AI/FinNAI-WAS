package com.finnai.domain.finance.entity;

import com.finnai.domain.company.entity.Company;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


/**
 * FinancialAccount기반으로 직접 계산
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "financial_index")
public class FinancialIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    // 공시 기준연도 (DART 재무제표 API: bsns_year)
    private Integer bsnsYear;
    // 보고서 코드 (DART reprt_code, 예: 11011=사업보고서)
    private String reprtCode;

    // 지표명 (내부 계산/정의  ROE, 부채비율 등; DART 원천값을 가공해 저장)
    private String indexNm;

    // 지표 값 (내부 계산/정의; 예: ROE, 부채비율 등)
    private Double indexValue;

    private LocalDateTime createdAt;
}
