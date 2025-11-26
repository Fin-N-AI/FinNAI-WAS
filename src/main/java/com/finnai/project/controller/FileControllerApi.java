package com.finnai.project.controller;


import com.finnai.project.dto.FileInsightDto;
import com.finnai.project.dto.FileUploadDto;
import com.finnai.project.response.GlobalApiResponse;
import com.finnai.project.response.SuccessCode;
import com.finnai.project.service.FileInsightService;
import com.finnai.project.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class FileControllerApi {

    private FileUploadService fileUploadService;
    private FileInsightService fileInsightService;

    @PostMapping("")
    public GlobalApiResponse<FileUploadDto>  fileUpload(@RequestParam("file")MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다.");
        }

        FileUploadDto fileUploadDto = fileUploadService.fileUpload(file);

        return GlobalApiResponse.success(SuccessCode.CREATED, fileUploadDto);
    }


    @GetMapping("/{fileSessionId}/insight")
    public GlobalApiResponse<FileInsightDto> insight(@PathVariable("fileSessionId") String fileSessionId) {
        if (fileSessionId == null || fileSessionId.isBlank()) {
            throw new IllegalArgumentException("파일 세션 ID가 올바르지 않습니다.");
        }

        FileInsightDto insightDto = fileInsightService.getInsight(fileSessionId);

        return GlobalApiResponse.success(SuccessCode.CREATED, insightDto);
    }



}
