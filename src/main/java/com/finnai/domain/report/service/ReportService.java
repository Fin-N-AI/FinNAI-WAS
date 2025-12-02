package com.finnai.domain.report.service;

import com.finnai.domain.report.dto.response.CompanyReportResp;

public interface ReportService {
    CompanyReportResp report(int companyId);
}
