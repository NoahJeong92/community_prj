package com.example.boardex.dto;


import com.example.boardex.domains.entity.Company;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CompanyFormDTO {

    private Long id;


    private String companyNm;


    private String companyDetail;



    private List<CompanyImgDTO> companyImgDtoList = new ArrayList<>();

    private List<Long> companyImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Company createCompany(){
        return modelMapper.map(this, Company.class);
    }

    public static CompanyFormDTO of(Company company){
        return modelMapper.map(company,CompanyFormDTO.class);
    }

}
