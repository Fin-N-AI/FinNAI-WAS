package com.finnai.domain.user.dto.file.response;

public record FileUploadResp (String fileName, String filePath , Long fileSize, String contentType) {
}
