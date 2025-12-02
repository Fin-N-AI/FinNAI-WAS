package com.finnai.domain.report.controller;

import com.finnai.domain.report.dto.response.CompanyReportResp;
import com.finnai.domain.report.service.ReportService;
import com.finnai.global.response.GlobalApiResponse;
import com.finnai.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
public class ReportControllerApi {

    private final ReportService reportService;

    @GetMapping("/{id}")
    public GlobalApiResponse<CompanyReportResp> report(@PathVariable("id") int companyId) {
        CompanyReportResp reportDto = reportService.report(companyId);
        return GlobalApiResponse.success(SuccessCode.SUCCESS, reportDto);
    }
}
