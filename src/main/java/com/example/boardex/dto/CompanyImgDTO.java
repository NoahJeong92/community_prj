package com.example.boardex.dto;


import com.example.boardex.domains.entity.CompanyImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class CompanyImgDTO {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static CompanyImgDTO of(CompanyImg companyImg){
        return modelMapper.map(companyImg, CompanyImgDTO.class);
    }

}
