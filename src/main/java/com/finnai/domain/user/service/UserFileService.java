package com.finnai.domain.user.service;

import com.finnai.domain.user.dto.file.response.FileInsightResp;
import com.finnai.domain.user.dto.file.response.FileUploadResp;
import org.springframework.web.multipart.MultipartFile;

public interface UserFileService {

    FileUploadResp fileUpload (MultipartFile file);

    FileInsightResp getInsight(String fileSessionId);
}
