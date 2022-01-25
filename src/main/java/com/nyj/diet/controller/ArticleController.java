package com.nyj.diet.controller;

import com.nyj.diet.domain.Article;
import com.nyj.diet.domain.Member;
import com.nyj.diet.dto.article.ArticleDTO;
import com.nyj.diet.dto.article.ArticleModifyForm;
import com.nyj.diet.dto.article.ArticleSaveForm;
import com.nyj.diet.service.ArticleService;
import com.nyj.diet.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final MemberService memberService;

    @GetMapping("/articles/write")
    public String showArticleWrite(Model model) {

        model.addAttribute("articleSaveForm", new ArticleSaveForm());

        return "usr/article/write";

    }

    // model이 프론트 단으로 넘겨주는 역할
    @PostMapping("/articles/write")
    public String doWrite(@Validated ArticleSaveForm articleSaveForm, BindingResult bindingResult, Model model, Principal principal) {

        if (bindingResult.hasErrors()) {
            return "usr/article/write";
        }

        try {

            Member findMember = memberService.findByLoginId(principal.getName());

            articleService.save(
                    articleSaveForm,
                    findMember
            );

        } catch (IllegalStateException e) {

            model.addAttribute("err_msg", e.getMessage());

            return "usr/article/write";

        }

        return "redirect:/";
    }

    // 수정
    @GetMapping("/articles/modify/{id}")
    public String showModify(@PathVariable(name="id") Long id, Model model){
        try{
            Article article = articleService.getById(id);
            model.addAttribute(
                    "articleModifyForm",
                    new ArticleModifyForm(
                            article.getTitle(),
                            article.getBody()
                    )
            );
            return "usr/article/modify";

        }catch (Exception e){

            return "redirect:/";
        }
    }

    @PostMapping("/articles/modify/{id}")
     public String doModify(@PathVariable(name="id") Long id, ArticleModifyForm articleModifyForm){
        try{
            articleService.modifyArticle(articleModifyForm, id);
            return "redirect:/articles/" + id;

        }catch (Exception e){
            return "usr/article/modify";
        }

    }


    // 삭제
    @GetMapping("/article/delete/{id}")
    public String deleteArticle(@PathVariable(name="id") Long id, Principal principal){

        try{
            ArticleDTO articleDTO = articleService.findArticle(id);

            if(articleDTO.getAuthorName() != principal.getName()){
                return "redirect:/";
            }

            articleService.delete(id);
            return "redirect:/";

        } catch (Exception e){
            return "redirect:/";
        }
    }


    // 리스트
    @GetMapping("/articles")
    public String showList(Model model){
        List<ArticleDTO> articleList = articleService.getArticleList();

        model.addAttribute("articleList", articleList);

        return "usr/article/list";
    }

}