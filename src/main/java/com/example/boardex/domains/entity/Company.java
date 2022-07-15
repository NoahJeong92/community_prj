package com.example.boardex.domains.entity;

import com.example.boardex.dto.CompanyFormDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="company")
@Getter @Setter
@ToString
public class Company extends BaseEntity{

    @Id
    @Column(name="company_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;       //회사 넘버

    @Column(nullable = false, length = 50)
    private String companyNm; //회사 이름

    @Lob
    @Column(nullable = false)
    private String companyDetail; //상품 상세 설명


    public void updateCompany(CompanyFormDTO companyFormDTO){
        this.companyNm = companyFormDTO.getCompanyNm();
        this.companyDetail = companyFormDTO.getCompanyDetail();

    }

}