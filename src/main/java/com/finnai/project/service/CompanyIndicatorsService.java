package com.finnai.project.service;

import com.finnai.project.dto.CompanyIndicatorsDto;

public interface CompanyIndicatorsService {

    CompanyIndicatorsDto getIndicators  (int companyId, String indicator);
}
