package com.finnai.domain.finance.dto.response;

import java.util.List;

/**
 * 재무제표 조회 응답
 */
public record FinancialsResp(
        Long companyId,
        String companyName,
        Integer year,
        String reportCode,
        List<AccountItem> accounts,
        FinancialSummary summary
) {
    /**
     * 재무 계정 항목
     */
    public record AccountItem(
            String accountId,
            String accountName,
            Long amount
    ) {}

    /**
     * 재무 요약 정보
     */
    public record FinancialSummary(
            Long totalAssets,      // 자산총계
            Long totalLiabilities, // 부채총계
            Long totalEquity,      // 자본총계
            Long revenue,          // 매출액
            Long operatingIncome,  // 영업이익
            Long netIncome         // 당기순이익
    ) {}
}
