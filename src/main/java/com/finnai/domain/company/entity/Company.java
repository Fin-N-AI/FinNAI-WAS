package com.finnai.domain.company.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


/**
 * https://opendart.fss.or.kr/api/company.json
 */
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

    // DART 고유 회사코드 (corp_code.zip: corp_code)
    private String corpCode;
    // DART 회사명 (corp_code.zip: corp_name)
    private String name;
    // DART 종목코드 (corp_code.zip: stock_code)
    private String stockCode;

    // 업종 코드 (DART corp_code.zip: induty_code)
    @Column(name = "induty_code")
    private String indutyCode;
    // 시장 구분 (예: KOSPI/KOSDAQ, 내부 관리용)
    private String market;

    // 회사 홈페이지 URL (내부 수집값)
    private String homepageUrl;
    // 본사 주소 (내부 수집값)
    private String headquartersAddr;

    // 설립일 (내부 수집값)
    private LocalDateTime foundedDate;

    // 법인등록번호 (내부 수집값)
    private String corporateRegNo;
    // 사업자등록번호 (내부 수집값)
    private String businessRegNo;
    // 대표전화 (내부 수집값)
    private String phoneNumber;
    // 대표이름
    private String ceoName;

    // 회사 개요 (요약, 내부 수집값)
    @Column(columnDefinition = "TEXT")
    private String overview;

    // 상세 설명 (내부 수집값)
    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
