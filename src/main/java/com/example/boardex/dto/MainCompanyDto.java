package com.example.boardex.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MainCompanyDto {

    private Long id;

    private String companyNm;

    private String companyDetail;

    private String imgUrl;


    @QueryProjection
    public MainCompanyDto(Long id, String companyNm, String companyDetail, String imgUrl){
        this.id = id;
        this.companyNm = companyNm;
        this.companyDetail = companyDetail;
        this.imgUrl = imgUrl;
    }

}
