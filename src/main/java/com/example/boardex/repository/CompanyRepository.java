package com.example.boardex.repository;

import com.example.boardex.domains.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long>, QuerydslPredicateExecutor<Company>, CompanyRepositoryCustom {

    List<Company> findByCompanyNm(String companyNm);

    List<Company> findByCompanyNmOrCompanyDetail(String companyNm, String companyDetail);


    @Query("select c from Company c where c.companyDetail like %:companyDetail% order by c.id desc")
    List<Company> findByCompanyDetail(@Param("companyDetail") String companyDetail);

    @Query(value="select * from Company c where c.company_detail like %:companyDetail% order by c.id desc", nativeQuery = true)
    List<Company> findByCompanyDetailByNative(@Param("companyDetail") String companyDetail);

}
