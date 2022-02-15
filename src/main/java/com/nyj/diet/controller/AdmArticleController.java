package com.nyj.diet.controller;

import com.nyj.diet.service.AdmArticleService;
import com.nyj.diet.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adm")
@RequiredArgsConstructor
public class AdmArticleController {

    private final AdmArticleService admArticleService;

    @GetMapping("/articles")
    public String showManageArticle(Model model){

        model.addAttribute("articles", admArticleService.getArticleList());

        return "adm/article/main";

    }

}
