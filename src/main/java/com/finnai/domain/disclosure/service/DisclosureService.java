package com.finnai.domain.disclosure.service;

import com.finnai.domain.disclosure.dto.response.DisclosureNewsResp;

public interface DisclosureService {
    DisclosureNewsResp news(int companyId);
}
