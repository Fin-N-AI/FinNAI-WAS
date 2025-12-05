package com.finnai.domain.company.dto.response;

import java.time.LocalDateTime;

public record CompanySummaryResp(
        Long id,
        String corpCode,
        String name,
        String stockCode,
        String indutyCode,
        String market,
        String homepageUrl,
        String headquartersAddr,
        LocalDateTime foundedDate,
        String corporateRegNo,
        String businessRegNo,
        String phoneNumber,
        String ceoName,
        String description,
        String overview
) {
}
