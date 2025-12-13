package com.finnai.domain.finance.dto.response;

import java.util.List;

/**
 * 재무지표 조회 응답
 */
public record FinancialIndicatorsResp(
        Long companyId,
        String companyName,
        String indicatorName,
        List<YearData> yearlyData
) {
    /**
     * 연도별 지표 데이터
     */
    public record YearData(
            Integer year,
            Double value,
            Double changeRate  // 전년 대비 변화율 (%)
    ) {}
}
