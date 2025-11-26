package com.finnai.project.service;

import com.finnai.project.dto.FileInsightDto;

public interface FileInsightService {
    FileInsightDto getInsight(String fileSessionId);
}
