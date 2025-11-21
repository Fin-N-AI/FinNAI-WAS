package com.finnai.project.dto;

public record FileUploadDto (String fileName, String filePath , Long fileSize, String contentType) {
}
