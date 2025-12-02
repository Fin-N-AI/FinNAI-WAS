package com.finnai.domain.disclosure.controller;

import com.finnai.domain.disclosure.dto.response.DisclosureNewsResp;
import com.finnai.domain.disclosure.service.DisclosureService;
import com.finnai.global.response.GlobalApiResponse;
import com.finnai.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/disclosure")
@RequiredArgsConstructor
public class DisclosureControllerApi {

    private final DisclosureService disclosureService;

    @GetMapping("/{id}/news")
    public GlobalApiResponse<DisclosureNewsResp> news(@PathVariable("id") int companyId) {
        DisclosureNewsResp newsDto = disclosureService.news(companyId);
        return GlobalApiResponse.success(SuccessCode.SUCCESS, newsDto);
    }
}
