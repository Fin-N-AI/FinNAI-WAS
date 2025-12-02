package com.finnai.domain.finance.service;

import com.finnai.domain.finance.dto.response.FinancialIndicatorsResp;
import com.finnai.domain.finance.dto.response.FinancialsResp;
import org.springframework.stereotype.Service;

@Service
public class FinancialServiceImpl implements FinancialService {

    @Override
    public FinancialsResp financials(int companyId) {
        return new FinancialsResp();
    }

    @Override
    public FinancialIndicatorsResp indicators(int companyId, String indicator) {
        return new FinancialIndicatorsResp();
    }
}
