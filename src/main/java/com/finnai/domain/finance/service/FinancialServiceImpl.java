package com.finnai.domain.finance.service;

import com.finnai.domain.company.entity.Company;
import com.finnai.domain.company.repository.CompanyRepository;
import com.finnai.domain.finance.dto.response.FinancialIndicatorsResp;
import com.finnai.domain.finance.dto.response.FinancialsResp;
import com.finnai.domain.finance.entity.FinancialAccount;
import com.finnai.domain.finance.entity.FinancialIndex;
import com.finnai.domain.finance.repository.FinancialAccountRepository;
import com.finnai.domain.finance.repository.FinancialIndexRepository;
import com.finnai.global.exceptioon.BusinessException;
import com.finnai.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FinancialServiceImpl implements FinancialService {

    private final CompanyRepository companyRepository;
    private final FinancialAccountRepository accountRepository;
    private final FinancialIndexRepository indexRepository;

    @Override
    @Transactional(readOnly = true)
    public FinancialsResp financials(int companyId) {
        // 1. 회사 존재 확인
        Company company = companyRepository.findById((long) companyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));

        // 2. 재무 계정 조회 (최신 연도 데이터만)
        List<FinancialAccount> accounts = accountRepository.findByCompanyIdOrderByBsnsYearDesc((long) companyId);

        if (accounts.isEmpty()) {
            throw new BusinessException(ErrorCode.FINANCIAL_DATA_NOT_FOUND);
        }

        // 3. 최신 연도와 보고서 코드 추출
        Integer latestYear = accounts.get(0).getBsnsYear();
        String reportCode = accounts.get(0).getReprtCode();

        // 4. 최신 연도 데이터만 필터링
        List<FinancialAccount> latestAccounts = accounts.stream()
                .filter(acc -> acc.getBsnsYear().equals(latestYear) && acc.getReprtCode().equals(reportCode))
                .collect(Collectors.toList());

        // 5. AccountItem 리스트 생성
        List<FinancialsResp.AccountItem> accountItems = latestAccounts.stream()
                .map(acc -> new FinancialsResp.AccountItem(
                        acc.getAccountId(),
                        acc.getAccountNm(),
                        acc.getThstrmAmount()
                ))
                .collect(Collectors.toList());

        // 6. 주요 재무 요약 정보 계산
        Map<String, Long> accountMap = new HashMap<>();
        for (FinancialAccount acc : latestAccounts) {
            accountMap.put(acc.getAccountNm(), acc.getThstrmAmount());
        }

        // 주요 계정명으로 매핑 (실제 DART API 응답의 계정명에 맞게 수정 필요)
        FinancialsResp.FinancialSummary summary = new FinancialsResp.FinancialSummary(
                accountMap.getOrDefault("자산총계", 0L),
                accountMap.getOrDefault("부채총계", 0L),
                accountMap.getOrDefault("자본총계", 0L),
                accountMap.getOrDefault("매출액", 0L),
                accountMap.getOrDefault("영업이익", 0L),
                accountMap.getOrDefault("당기순이익", 0L)
        );

        return new FinancialsResp(
                company.getId(),
                company.getName(),
                latestYear,
                reportCode,
                accountItems,
                summary
        );
    }

    @Override
    @Transactional(readOnly = true)
    public FinancialIndicatorsResp indicators(int companyId, String indicator) {
        // 1. 회사 존재 확인
        Company company = companyRepository.findById((long) companyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));

        // 2. 특정 지표 조회 (여러 연도)
        List<FinancialIndex> indices = indexRepository.findByCompanyIdAndIndexNmOrderByBsnsYearDesc(
                (long) companyId, indicator);

        if (indices.isEmpty()) {
            throw new BusinessException(ErrorCode.FINANCIAL_DATA_NOT_FOUND);
        }

        // 3. 연도별 데이터 변환 및 변화율 계산
        List<FinancialIndicatorsResp.YearData> yearlyData = new ArrayList<>();

        for (int i = 0; i < indices.size(); i++) {
            FinancialIndex current = indices.get(i);
            Double changeRate = null;

            // 전년도 데이터가 있으면 변화율 계산
            if (i < indices.size() - 1) {
                FinancialIndex previous = indices.get(i + 1);
                if (previous.getIndexValue() != null && previous.getIndexValue() != 0) {
                    changeRate = ((current.getIndexValue() - previous.getIndexValue())
                            / previous.getIndexValue()) * 100;
                }
            }

            yearlyData.add(new FinancialIndicatorsResp.YearData(
                    current.getBsnsYear(),
                    current.getIndexValue(),
                    changeRate
            ));
        }

        return new FinancialIndicatorsResp(
                company.getId(),
                company.getName(),
                indicator,
                yearlyData
        );
    }
}
