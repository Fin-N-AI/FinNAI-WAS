package com.finnai.domain.user.service;

import com.finnai.domain.user.dto.file.response.FileInsightResp;
import com.finnai.domain.user.dto.file.response.FileUploadResp;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UserFileServiceImpl implements UserFileService {

    @Override
    public FileUploadResp fileUpload(MultipartFile file) {
        return null;
    }

    @Override
    public FileInsightResp getInsight(String fileSessionId) {
        return null;
    }
}
