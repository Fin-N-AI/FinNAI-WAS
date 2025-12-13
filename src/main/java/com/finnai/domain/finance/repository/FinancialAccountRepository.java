package com.finnai.domain.finance.repository;

import com.finnai.domain.finance.entity.FinancialAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialAccountRepository extends JpaRepository<FinancialAccount, Long> {

    /**
     * 특정 회사의 모든 재무 계정 조회 (최신 연도순)
     */
    List<FinancialAccount> findByCompanyIdOrderByBsnsYearDesc(Long companyId);

    /**
     * 특정 회사의 특정 연도 재무 계정 조회
     */
    List<FinancialAccount> findByCompanyIdAndBsnsYear(Long companyId, Integer bsnsYear);

    /**
     * 특정 회사의 특정 연도, 특정 보고서 코드의 재무 계정 조회
     */
    List<FinancialAccount> findByCompanyIdAndBsnsYearAndReprtCode(Long companyId, Integer bsnsYear, String reprtCode);
}
