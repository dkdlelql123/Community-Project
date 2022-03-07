package com.nyj.diet.controller;

import com.nyj.diet.domain.Member;
import com.nyj.diet.dto.Board.BoardDTO;
import com.nyj.diet.dto.article.ArticleListDTO;
import com.nyj.diet.service.ArticleService;
import com.nyj.diet.service.BoardService;
import com.nyj.diet.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ArticleService articleService;
    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("/")
    public String showIndex(Model model, Principal principal) {

        List<BoardDTO> boardList = boardService.getBoards();
        model.addAttribute("boards", boardList);

        List<ArticleListDTO> articleList = articleService.getHighViewedArticles();
        List<ArticleListDTO> topTenArticle = articleList.subList(0, 10);
        model.addAttribute("articles", topTenArticle);

//        if(principal != null){
//            String memberId = principal.getName();
//            Member findMember = memberService.findByLoginId(memberId);
//            model.addAttribute("member", findMember );
//        }

        return "index";
    }
}
