package com.finnai.domain.user.dto.file.response;

public record FileInsightResp(
        String fileSessionId,
        String fileName,
        String summary
) {
}

