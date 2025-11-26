package com.finnai.project.service;

import com.finnai.project.dto.FileInsightDto;

public interface FileInsightInterface {
    FileInsightDto getInsight(String fileSessionId);
}
