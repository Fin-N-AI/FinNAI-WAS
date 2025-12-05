package com.finnai.domain.company.service;

import com.finnai.domain.company.dto.response.CompanySummariesResp;
import com.finnai.domain.company.dto.response.CompanySummaryResp;

public interface CompanyService {

    CompanySummaryResp summary (long companyId);
    CompanySummariesResp summaries (int page, int size);

}
