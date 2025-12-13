package com.finnai.domain.finance.repository;

import com.finnai.domain.finance.entity.FinancialIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialIndexRepository extends JpaRepository<FinancialIndex, Long> {

    /**
     * 특정 회사의 모든 재무 지표 조회 (최신 연도순)
     */
    List<FinancialIndex> findByCompanyIdOrderByBsnsYearDesc(Long companyId);

    /**
     * 특정 회사의 특정 지표 조회 (최신 연도순)
     */
    List<FinancialIndex> findByCompanyIdAndIndexNmOrderByBsnsYearDesc(Long companyId, String indexNm);

    /**
     * 특정 회사의 특정 연도 재무 지표 조회
     */
    List<FinancialIndex> findByCompanyIdAndBsnsYear(Long companyId, Integer bsnsYear);
}
