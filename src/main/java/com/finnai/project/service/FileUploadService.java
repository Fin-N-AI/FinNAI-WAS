package com.finnai.project.service;

import com.finnai.project.dto.FileUploadDto;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    FileUploadDto fileUpload (MultipartFile file);

}
