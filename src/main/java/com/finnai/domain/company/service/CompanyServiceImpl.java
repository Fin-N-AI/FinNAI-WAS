package com.finnai.domain.company.service;

import com.finnai.domain.company.dto.response.CompanySummariesResp;
import com.finnai.domain.company.dto.response.CompanySummariesResp.CompanySummaryItem;
import com.finnai.domain.company.dto.response.CompanySummaryResp;
import com.finnai.domain.company.entity.Company;
import com.finnai.domain.company.repository.CompanyRepository;
import com.finnai.global.exceptioon.BusinessException;
import com.finnai.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private static final int MAX_PAGE_SIZE = 10;

    private final CompanyRepository companyRepository;

    @Override
    public CompanySummaryResp summary(long companyId) {
        Company company = getCompany(companyId);
        return mapToSummary(company);
    }

    @Override
    public CompanySummariesResp summaries(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<Company> pageResult = companyRepository.findAll(pageable);

        List<CompanySummaryItem> companies = pageResult.stream()
                .map(this::mapToSummaryItem)
                .toList();

        return new CompanySummariesResp(
                companies,
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages(),
                pageResult.hasNext()
        );
    }


    private Company getCompany(long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPANY_NOT_FOUND));
    }

    private CompanySummaryResp mapToSummary(Company company) {
        return new CompanySummaryResp(
                company.getId(),
                company.getCorpCode(),
                company.getName(),
                company.getStockCode(),
                company.getIndutyCode(),
                company.getMarket(),
                company.getHomepageUrl(),
                company.getHeadquartersAddr(),
                company.getFoundedDate(),
                company.getCorporateRegNo(),
                company.getBusinessRegNo(),
                company.getPhoneNumber(),
                company.getCeoName(),
                company.getDescription(),
                company.getOverview()
        );
    }

    private CompanySummaryItem mapToSummaryItem(Company company) {
        return new CompanySummaryItem(
                company.getId(),
                company.getName(),
                company.getDescription(),
                company.getOverview()
        );
    }
}
