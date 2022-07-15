package com.example.boardex.service;

import com.example.boardex.dto.GuestbookDTO;
import com.example.boardex.dto.PageRequestDTO;
import com.example.boardex.dto.PageResultDTO;
import com.example.boardex.domains.entity.Guestbook;

public interface GuestbookService {

    //등록
    Long register(GuestbookDTO dto);

    PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);

    GuestbookDTO read(Long gno);

    void modify(GuestbookDTO dto);

    void remove(Long gno);


    //나중에 dto와 entity 구조가 달라지는 경우가 생김
    //
    default Guestbook dtoToEntity(GuestbookDTO dto)
    {
        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();

        return entity;
    }

    default GuestbookDTO entityToDto(Guestbook entity)
    {
            GuestbookDTO dto = GuestbookDTO.builder()
                    .gno(entity.getGno())
                    .title(entity.getTitle())
                    .content(entity.getContent())
                    .writer(entity.getWriter())
                    .regDate(entity.getRegDate())
                    .modDate(entity.getModdate())
                    .build();

            return dto;
    }
}
