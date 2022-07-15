package com.example.boardex.service;

import com.example.boardex.domains.entity.QGuestbook;
import com.example.boardex.dto.GuestbookDTO;
import com.example.boardex.dto.PageRequestDTO;
import com.example.boardex.dto.PageResultDTO;
import com.example.boardex.domains.entity.Guestbook;
import com.example.boardex.repository.GuestbookRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService{

private final GuestbookRepository repository;

    //글 작성
    @Override
    public Long register(GuestbookDTO dto){
        log.info("DTO-----------------");
        log.info(dto);
        Guestbook entity = dtoToEntity(dto);
        log.info(entity);
        repository.save(entity);
        return entity.getGno();

    }
    //조회
    @Override
    public  GuestbookDTO read(Long gno) {
        Optional<Guestbook> result = repository.findById(gno);
        return result.isPresent()? entityToDto(result.get()): null;

    }
    //삭제
    @Override
    public void remove(Long gno){
        repository.deleteById(gno);
    }

    //수정
    @Override
    public void modify(GuestbookDTO dto){
        Optional<Guestbook> result = repository.findById(dto.getGno());
            if(result.isPresent()){
                Guestbook entity = result.get();

                entity.changeTitle(dto.getTitle());
                entity.changeContent(dto.getContent());

                repository.save(entity);
            }
        }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO){

        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qGuestbook.gno.gt(0L); // gno > 0 조건만 생성

        booleanBuilder.and(expression);

        if(type == null || type.trim().length() == 0){ //검색 조건이 없는 경우
            return booleanBuilder;
        }


        //검색 조건을 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if(type.contains("t")){
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        if(type.contains("c")){
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        }
        if(type.contains("w")){
            conditionBuilder.or(qGuestbook.writer.contains(keyword));
        }

        //모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }



    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        Page<Guestbook> result = repository.findAll(pageable);

        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity)); //entity과 들어오면 dto 변환 functon을 통해서 PageResultDTO를 반환

        return new PageResultDTO<>(result, fn );
    }
}
