package com.finnai.domain.company.service;

import com.finnai.domain.company.dto.response.CompanyOverviewResp;
import com.finnai.domain.company.dto.response.CompanySummariesResp;
import com.finnai.domain.company.dto.response.CompanySummaryResp;
import com.finnai.domain.company.dto.response.CompanyWatchListResp;

public interface CompanyService {

    CompanySummaryResp summary (int companyId);
    CompanySummariesResp summaries (int companyId);
    CompanyOverviewResp overview (int companyId);
    CompanyWatchListResp watchList (int companyId);

}
