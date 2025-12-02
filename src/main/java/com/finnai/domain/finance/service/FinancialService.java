package com.finnai.domain.finance.service;

import com.finnai.domain.finance.dto.response.FinancialIndicatorsResp;
import com.finnai.domain.finance.dto.response.FinancialsResp;

public interface FinancialService {
    FinancialsResp financials(int companyId);
    FinancialIndicatorsResp indicators(int companyId, String indicator);
}
