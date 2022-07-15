package com.example.boardex.service;

import com.example.boardex.domains.entity.CompanyImg;
import com.example.boardex.repository.CompanyImgRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyImgService {

    @Value("${companyImgLocation}")
    private String companyImgLocation;

    private final CompanyImgRepository companyImgRepository;

    private final FileService fileService;

    public void saveCompanyImg(CompanyImg companyImg, MultipartFile companyImgFile) throws Exception{
        String oriImgName = companyImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(companyImgLocation, oriImgName, companyImgFile.getBytes());
            imgUrl = "/images/company/" + imgName;
        }

        //상품 이미지 정보 저장
        companyImg.updateItemImg(oriImgName, imgName, imgUrl);
        companyImgRepository.save(companyImg);
    }

    public void updateCompanyImg(Long companyImgId, MultipartFile companyImgFile) throws Exception{
        if(!companyImgFile.isEmpty()){
            CompanyImg savedCompanyImg = companyImgRepository.findById(companyImgId)
                    .orElseThrow(EntityNotFoundException::new);

            String imgUrl = savedCompanyImg.getImgUrl();

            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedCompanyImg.getImgName())) {
                fileService.deleteFile(imgUrl);
            }

            String oriImgName = companyImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(companyImgLocation, oriImgName, companyImgFile.getBytes());
            imgUrl = "/images/company/" + imgName;

            savedCompanyImg.updateItemImg(oriImgName, imgName, imgUrl);
        }
    }

}
