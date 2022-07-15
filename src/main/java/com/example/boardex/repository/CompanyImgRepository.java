package com.example.boardex.repository;


import com.example.boardex.domains.entity.CompanyImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyImgRepository extends JpaRepository<CompanyImg, Long> {

    List<CompanyImg> findByCompanyIdOrderByIdAsc(Long companyId);

    CompanyImg findByCompanyIdAndRepimgYn(Long companyId, String repimgYn);
}
