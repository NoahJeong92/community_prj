package com.example.boardex.service;

import com.example.boardex.dto.GuestbookDTO;
import com.example.boardex.dto.PageRequestDTO;
import com.example.boardex.dto.PageResultDTO;
import com.example.boardex.domains.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class GuestbookServiceTest {

    @Autowired
    GuestbookService service;

    @Test
    public  void testRegister(){
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample Title....")
                .content("Contest.....")
                .writer("정태환")
                .build();
        System.out.println(service.register(guestbookDTO));
    }

    @Test
    public void testList(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

//        System.out.println("PREV: "+resultDTO.isPrev());
//        System.out.println("NEXT: "+resultDTO.isNext());
//        System.out.println("TOTAL: " + resultDTO.getTotalPage());

        System.out.println("-------------------------------------");
        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
            System.out.println(guestbookDTO);
        }

//        System.out.println("========================================");
//        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }
}