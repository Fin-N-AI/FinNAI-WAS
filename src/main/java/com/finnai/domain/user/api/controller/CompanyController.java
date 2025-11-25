package com.finnai.domain.user.api.controller;


@RestController
@RequestMapping("/v1/company")
public class CompanyController {

    @GetmApping("/info")
    public String getCompanyInfo() {
        return "Company Info";
    }

    @PostMapping("/create")
    public String createCompany() {
        return "Company Created";
    }   
}