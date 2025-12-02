package com.finnai.domain.disclosure.service;

import com.finnai.domain.disclosure.dto.response.DisclosureNewsResp;
import org.springframework.stereotype.Service;

@Service
public class DisclosureServiceImpl implements DisclosureService {
    @Override
    public DisclosureNewsResp news(int companyId) {
        return new DisclosureNewsResp();
    }
}
