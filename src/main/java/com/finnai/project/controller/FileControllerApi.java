package com.finnai.project.controller;


import com.finnai.project.dto.FileInsightDto;
import com.finnai.project.dto.FileUploadDto;
import com.finnai.project.service.FileInsightInterface;
import com.finnai.project.service.FileUploadInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class FileControllerApi {

    private FileUploadInterface fileUploadInterface;
    private FileInsightInterface fileInsightInterface;

    @PostMapping("")
    public FileUploadDto fileUpload(@RequestParam("file")MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다.");
        }
        return fileUploadInterface.fileUpload(file);
    }


    @GetMapping("/{fileSessionId}/insight")
    public FileInsightDto insight(@PathVariable("fileSessionId") String fileSessionId) {
        if (fileSessionId == null || fileSessionId.isBlank()) {
            throw new IllegalArgumentException("파일 세션 ID가 올바르지 않습니다.");
        }
        return fileInsightInterface.getInsight(fileSessionId);
    }



}
