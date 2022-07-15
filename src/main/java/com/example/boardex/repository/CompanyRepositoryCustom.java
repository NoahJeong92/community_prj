package com.example.boardex.repository;

import com.example.boardex.dto.CompanySearchDto;
import com.example.boardex.dto.MainCompanyDto;
import com.example.boardex.domains.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyRepositoryCustom {

    Page<Company> getAdminCompanyPage(CompanySearchDto companySearchDto, Pageable pageable);

    Page<MainCompanyDto> getMainCompanyPage(CompanySearchDto companySearchDto, Pageable pageable);

}
