package com.finnai.domain.report.service;

import com.finnai.domain.report.dto.response.CompanyReportResp;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {
    @Override
    public CompanyReportResp report(int companyId) {
        return new CompanyReportResp();
    }
}
