package com.finnai.domain.user.controller;


import com.finnai.domain.user.dto.file.response.FileInsightResp;
import com.finnai.domain.user.dto.file.response.FileUploadResp;
import com.finnai.domain.user.service.UserFileService;
import com.finnai.global.response.GlobalApiResponse;
import com.finnai.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class FileControllerApi {

    private final UserFileService userFileService;

    @PostMapping("")
    public GlobalApiResponse<FileUploadResp> fileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다.");
        }

        FileUploadResp fileUploadDto = userFileService.fileUpload(file);

        return GlobalApiResponse.success(SuccessCode.CREATED, fileUploadDto);
    }


    @GetMapping("/{fileSessionId}/insight")
    public GlobalApiResponse<FileInsightResp> insight(@PathVariable("fileSessionId") String fileSessionId) {
        if (fileSessionId == null || fileSessionId.isBlank()) {
            throw new IllegalArgumentException("파일 세션 ID가 올바르지 않습니다.");
        }

        FileInsightResp insightDto = userFileService.getInsight(fileSessionId);

        return GlobalApiResponse.success(SuccessCode.CREATED, insightDto);
    }

}
