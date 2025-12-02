package com.finnai.domain.disclosure.entity;

import com.finnai.domain.company.entity.Company;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * https://opendart.fss.or.kr/api/list.json
 */
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

    // 공시 수신번호 (DART 공시검색: rcept_no)
    @Column(nullable = false, unique = true)
    private String rceptNo;

    // 보고서명 (DART 공시검색: report_nm)
    private String reportNm;

    // 접수일자 (YYYY-MM-DD, DART 공시검색: rcept_dt)
    private LocalDate rceptDt;

    // 보고서 유형 코드 (DART 공시검색: rpt_type, 없으면 null)
    private String rptType;

    // 공시 제출인/발행인 명 (DART 공시검색: flr_nm)
    private String flrNm;

    private LocalDateTime createdAt;
}
