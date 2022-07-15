package com.example.boardex.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CompanyDTO {

    private Long id;

    private String companyNm; //회사 이름

    private String companyDetail; //상품 상세 설명

    private LocalDateTime regDate, modDate;

}
