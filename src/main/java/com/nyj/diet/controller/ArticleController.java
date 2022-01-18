package com.nyj.diet.controller;

import com.nyj.diet.domain.Member;
import com.nyj.diet.dto.article.ArticleSaveForm;
import com.nyj.diet.service.ArticleService;
import com.nyj.diet.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final MemberService memberService;

    @GetMapping("/articles/write")
    public String showWrite(Model model){

        model.addAttribute("articleSaveForm", new ArticleSaveForm());

        return "usr/article/write";
    }

    // model이 프론트 단으로 넘겨주는 역할
    @PostMapping("/articles/write")
    public String doWrite(@Validated ArticleSaveForm articleSaveForm, BindingResult bindingResult, Model model, Principal principal){

        if(bindingResult.hasErrors()){
            return "usr/article/write";
        }

        try {

            // principal 멤버를 받음
            Member findMember = memberService.findByLoginId(principal.getName());

            articleService.save(
                    articleSaveForm,
                    findMember
            );

        } catch (IllegalStateException e){

            model.addAttribute("err_msg",e.getMessage());

            return "usr/article/write";

        }

        return "redirect:/";
    }
}
