package com.finnai.domain.company.repository;

import com.finnai.domain.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    /**
     * 종목코드로 회사 조회
     */
    Optional<Company> findByStockCode(String stockCode);

    /**
     * DART 회사코드로 회사 조회
     */
    Optional<Company> findByCorpCode(String corpCode);
}
