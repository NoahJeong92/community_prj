package com.example.boardex.controller;

import com.example.boardex.dto.CompanySearchDto;
import com.example.boardex.dto.MainCompanyDto;
import com.example.boardex.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final CompanyService companyService;

    @GetMapping(value = "/")
    public String main(CompanySearchDto companySearchDto, Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<MainCompanyDto> companys = companyService.getMainCompanyPage(companySearchDto, pageable);
        model.addAttribute("companys", companys);
        model.addAttribute("companySearchDto", companySearchDto);
        model.addAttribute("maxPage", 5);
        return "company/main";
    }

}
