package com.nyj.diet.controller;

import com.nyj.diet.dto.Board.BoardDTO;
import com.nyj.diet.dto.article.ArticleDTO;
import com.nyj.diet.dto.article.ArticleListDTO;
import com.nyj.diet.service.ArticleService;
import com.nyj.diet.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ArticleService articleService;
    private final BoardService boardService;

    @GetMapping("/")
    public String showIndex(Model model) {

        List<BoardDTO> boardList = boardService.getBoards();
        model.addAttribute("boards", boardList);

        List<ArticleListDTO> articleList = articleService.getHighViewedArticles();
        model.addAttribute("articles", articleList);

        return "index";
    }
}
