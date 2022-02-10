package com.nyj.diet.controller;

import com.nyj.diet.dto.Board.BoardDTO;
import com.nyj.diet.service.ArticleService;
import com.nyj.diet.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ArticleService articleService;
    private final BoardService boardService;

    @GetMapping("/")
    public String showIndex(Model model) {

        long boardId = 1;
//        BoardDTO findBoardDetail = boardService.getNewBoardDetail(boardId);
//        model.addAttribute("boards", findBoardDetail);

        return "index"; //주소를 뱉는다.
    }
}
