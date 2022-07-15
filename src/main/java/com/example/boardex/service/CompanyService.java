package com.example.boardex.service;

import com.example.boardex.dto.CompanyFormDTO;
import com.example.boardex.dto.CompanyImgDTO;
import com.example.boardex.dto.CompanySearchDto;
import com.example.boardex.dto.MainCompanyDto;
import com.example.boardex.domains.entity.Company;
import com.example.boardex.domains.entity.CompanyImg;
import com.example.boardex.repository.CompanyImgRepository;
import com.example.boardex.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyImgService companyImgService;
    private final CompanyImgRepository companyImgRepository;

    public Long saveCompany(CompanyFormDTO companyFormDTO, List<MultipartFile> companyImgFileList) throws Exception{

        //학원 등록

        Company company = companyFormDTO.createCompany();
        companyRepository.save(company);

        //이미지 등록
        for(int i=0;i<companyImgFileList.size();i++){
            CompanyImg companyImg = new CompanyImg();
            companyImg.setCompany(company);

            if(i == 0)
                companyImg.setRepimgYn("Y");
            else
                companyImg.setRepimgYn("N");

            companyImgService.saveCompanyImg(companyImg, companyImgFileList.get(i));
        }

        return company.getId();
    }

    public Long updateCompany(CompanyFormDTO companyFormDTO, List<MultipartFile> companyImgFileList) throws Exception{

        //회사 수정
        Company company = companyRepository.findById(companyFormDTO.getId())
                .orElseThrow(EntityNotFoundException::new);
        company.updateCompany(companyFormDTO);


        List<Long> companyImgIds = companyFormDTO.getCompanyImgIds();

        //이미지 등록
        for(int i=0;i<companyImgFileList.size();i++){
            companyImgService.updateCompanyImg(companyImgIds.get(i), companyImgFileList.get(i));
        }

        return company.getId();
    }

    @Transactional(readOnly = true)
    public CompanyFormDTO getCompanyDtl(Long companyId){

        List<CompanyImg> companyImgList = companyImgRepository.findByCompanyIdOrderByIdAsc(companyId);
        List<CompanyImgDTO> companyImgDtoList = new ArrayList<>();
        for (CompanyImg companyImg : companyImgList) {
            CompanyImgDTO companyImgDTO= CompanyImgDTO.of(companyImg);
            companyImgDtoList.add(companyImgDTO);
        }

        Company company = companyRepository.findById(companyId)
                .orElseThrow(EntityNotFoundException::new);
        CompanyFormDTO companyFormDTO = CompanyFormDTO.of(company);
        companyFormDTO.setCompanyImgDtoList(companyImgDtoList);
        return companyFormDTO;
    }

    @Transactional(readOnly = true)
    public Page<Company> getAdminCompanyPage(CompanySearchDto companySearchDto, Pageable pageable){
        return companyRepository.getAdminCompanyPage(companySearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainCompanyDto> getMainCompanyPage(CompanySearchDto companySearchDto, Pageable pageable){
        return companyRepository.getMainCompanyPage(companySearchDto, pageable);
    }

}
