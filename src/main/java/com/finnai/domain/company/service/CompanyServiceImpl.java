package com.finnai.domain.company.service;

import com.finnai.domain.company.dto.response.CompanyOverviewResp;
import com.finnai.domain.company.dto.response.CompanySummariesResp;
import com.finnai.domain.company.dto.response.CompanySummaryResp;
import com.finnai.domain.company.dto.response.CompanyWatchListResp;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Override
    public CompanySummaryResp summary(int companyId) {
        return new CompanySummaryResp();
    }

    @Override
    public CompanySummariesResp summaries(int companyId) {
        return new CompanySummariesResp();
    }

    @Override
    public CompanyOverviewResp overview(int companyId) {
        return new CompanyOverviewResp();
    }

    @Override
    public CompanyWatchListResp watchList(int companyId) {
        return new CompanyWatchListResp();
    }
}
