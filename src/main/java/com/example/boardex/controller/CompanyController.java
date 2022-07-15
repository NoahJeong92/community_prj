package com.example.boardex.controller;

import com.example.boardex.dto.CompanyFormDTO;
import com.example.boardex.dto.CompanySearchDto;
import com.example.boardex.domains.entity.Company;
import com.example.boardex.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping(value = "/admin/company/new")
    public String companyForm(Model model){
        model.addAttribute("companyFormDTO", new CompanyFormDTO());
        return "company/companyForm";
    }

    @PostMapping(value = "/admin/company/new")
    public String companyNew(@Valid CompanyFormDTO companyFormDTO, BindingResult bindingResult, Model model,
                             @RequestParam("companyImgFile") List<MultipartFile> companyImgFileList){

        if(bindingResult.hasErrors()){
            return "company/companyForm";
        }

        if(companyImgFileList.get(0).isEmpty() && companyFormDTO.getId() == null){
            model.addAttribute("errorMessage", "대표 이미지는 필수 입력 값 입니다.");
            return "company/companyForm";
        }

        try {
            companyService.saveCompany(companyFormDTO, companyImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "학원 등록 중 에러가 발생하였습니다.");
            return "company/companyForm";
        }

        return "redirect:/company";
    }

    @GetMapping(value = "/admin/company/{companyId}")
    public String companyDtl(@PathVariable("companyId") Long itemId, Model model){

        try {
            CompanyFormDTO companyFormDTO = companyService.getCompanyDtl(itemId);
            model.addAttribute("companyFormDTO", companyFormDTO);
        } catch(EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 학원 입니다.");
            model.addAttribute("companyFormDTO", new CompanyFormDTO());
            return "company/companyForm";
        }

        return "company/companyForm";
    }

    @PostMapping(value = "/admin/company/{companyId}")
    public String companyUpdate(@Valid CompanyFormDTO companyFormDTO, BindingResult bindingResult, @RequestParam("companyImgFile") List<MultipartFile> companyImgFileList, Model model){

        if(bindingResult.hasErrors()){
            return "company/companyForm";
        }

        if(companyImgFileList.get(0).isEmpty() && companyFormDTO.getId() == null){
            model.addAttribute("errorMessage", "첫번째 이미지는 필수 입력 값 입니다.");
            return "company/companyForm";
        }

        try {
            companyService.updateCompany(companyFormDTO, companyImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "수정 중 에러가 발생하였습니다.");
            return "company/companyForm";
        }

        return "redirect:/company";
    }

    @GetMapping(value = {"/admin/companys", "/admin/companys/{page}"})
    public String companyManage(CompanySearchDto companySearchDto, @PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<Company> companys = companyService.getAdminCompanyPage(companySearchDto, pageable);
        model.addAttribute("companys", companys);
        model.addAttribute("companySearchDto", companySearchDto);
        model.addAttribute("maxPage", 5);
        return "company/companyMng";
    }

    @GetMapping(value = "/company/{companyId}")
    public String companyDtl(Model model, @PathVariable("companyId") Long companyId){
       CompanyFormDTO companyFormDTO = companyService.getCompanyDtl(companyId);
        model.addAttribute("company", companyFormDTO);
        return "company/companyDtl";
    }


}
