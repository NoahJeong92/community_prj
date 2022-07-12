package com.example.boardex.service;

import com.example.boardex.dto.GuestbookDTO;
import com.example.boardex.dto.PageRequestDTO;
import com.example.boardex.dto.PageResultDTO;
import com.example.boardex.entity.Guestbook;
import com.example.boardex.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService{

private final GuestbookRepository repository;

    @Override
    public Long register(GuestbookDTO dto){
        log.info("DTO-----------------");
        log.info(dto);
        Guestbook entity = dtoToEntity(dto);
        log.info(entity);
        repository.save(entity);
        return entity.getGno();

    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        Page<Guestbook> result = repository.findAll(pageable);

        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity)); //entity과 들어오면 dto 변환 functon을 통해서 PageResultDTO를 반환

        return new PageResultDTO<>(result, fn );
    }
}
