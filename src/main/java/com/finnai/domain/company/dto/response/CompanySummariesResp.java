package com.finnai.domain.company.dto.response;

import java.util.List;

public record CompanySummariesResp(
        List<CompanySummaryItem> companies,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext
) {
    public record CompanySummaryItem(
            Long companyId,
            String name,
            String description,
            String overview
    ) {
    }
}
