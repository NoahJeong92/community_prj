package com.example.boardex.controller;


import com.example.boardex.dto.GuestbookDTO;
import com.example.boardex.dto.PageRequestDTO;
import com.example.boardex.service.GuestbookService;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor

public class GuestbookController {

    private final GuestbookService service;


    @GetMapping("/list")
    public void list(@ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO,
                     Model model){
        log.info("list.........." + pageRequestDTO);
        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public  void register(){
        log.info("register get..");
    }
    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes){
        log.info("dto..."+ dto);
        Long gno = service.register(dto);
        redirectAttributes.addAttribute("msg", gno);
        return "redirect:/guestbook/list";
    }
}
