package com.example.boardex.dto;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CompanySearchDto {

    private String searchDateType;


    private String searchBy;

    private String searchQuery = "";

}
