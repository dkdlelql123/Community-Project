package com.nyj.diet.controller;

import com.nyj.diet.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adm")
@RequiredArgsConstructor
public class AdmBoardController {

    private final BoardService boardService;

    // 작업해야할 것
    // 저장, 수정, 삭제, 리스트

    @GetMapping("/boards")
    public String showManageBoards(Model model){

        model.addAttribute("boards",boardService.getBoardList());

        return "adm/board/main";
    }

    @GetMapping("/boards/modify/{id}")
    public String showBoardDetail(@PathVariable(name = "id") Long id, Model model){
        return "redirect:/adm/boards";
    }


    @GetMapping("/boards/delete/{id}")
    public String deleteBoard(@PathVariable(name = "id") Long id){
        return "redirect:/adm/boards";
    }
}
