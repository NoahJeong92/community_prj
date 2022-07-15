package com.example.boardex.repository;

import com.example.boardex.domains.entity.QCompany;
import com.example.boardex.domains.entity.QCompanyImg;
import com.example.boardex.dto.CompanySearchDto;
import com.example.boardex.dto.MainCompanyDto;
import com.example.boardex.dto.QMainCompanyDto;
import com.example.boardex.domains.entity.Company;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class CompanyRepositoryCustomImpl implements CompanyRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public CompanyRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }


    private BooleanExpression searchByLike(String searchBy, String searchQuery){

        if(StringUtils.equals("itemNm", searchBy)){
            return QCompany.company.companyNm.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("createdBy", searchBy)){
            return QCompany.company.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }

    private BooleanExpression regDtsAfter(String searchDateType){
        LocalDateTime dateTime = LocalDateTime.now();

        if(StringUtils.equals("all", searchDateType) || searchDateType == null){
            return null;
        } else if(StringUtils.equals("1d", searchDateType)){
            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("1w", searchDateType)){
            dateTime = dateTime.minusWeeks(1);
        } else if(StringUtils.equals("1m", searchDateType)){
            dateTime = dateTime.minusMonths(1);
        } else if(StringUtils.equals("6m", searchDateType)){
            dateTime = dateTime.minusMonths(6);
        }

        return QCompany.company.regDate.after(dateTime);
    }

    @Override
    public Page<Company> getAdminCompanyPage(CompanySearchDto companySearchDto, Pageable pageable) {
        QueryResults<Company> results = queryFactory
                .selectFrom(QCompany.company)
                .where(regDtsAfter(companySearchDto.getSearchDateType()),
                        searchByLike(companySearchDto.getSearchBy(), companySearchDto.getSearchQuery()))
                .orderBy(QCompany.company.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Company> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression companyNmLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null : QCompany.company.companyNm.like("%" + searchQuery + "%");
    }

    @Override
    public Page<MainCompanyDto> getMainCompanyPage(CompanySearchDto companySearchDto, Pageable pageable) {
        QCompany company = QCompany.company;
        QCompanyImg companyImg = QCompanyImg.companyImg;

        QueryResults<MainCompanyDto> results =  queryFactory
                .select(
                        new QMainCompanyDto(
                        company.id,
                        company.companyNm,
                        company.companyDetail,
                        companyImg.imgUrl
                        )
                )
                .from(companyImg)
                .join(companyImg.company, company)
                .where(companyImg.repimgYn.eq("Y"))
                .where(companyNmLike(companySearchDto.getSearchQuery()))
                .orderBy(company.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MainCompanyDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }
}
