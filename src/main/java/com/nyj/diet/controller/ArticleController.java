package com.nyj.diet.controller;

import com.nyj.diet.domain.Article;
import com.nyj.diet.domain.Board;
import com.nyj.diet.domain.Member;
import com.nyj.diet.dto.Board.BoardDTO;
import com.nyj.diet.dto.article.ArticleDTO;
import com.nyj.diet.dto.article.ArticleModifyForm;
import com.nyj.diet.dto.article.ArticleSaveForm;
import com.nyj.diet.service.ArticleService;
import com.nyj.diet.service.BoardService;
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
    private final BoardService boardService;

    @GetMapping("/boards/{id}/articles/write")
    public String showArticleWrite(@PathVariable(name = "id") Long id, Model model) {

//        이렇게 넣을 경우 html th에 넣을 시 error 발생
//        BoardDTO boardDetail = boardService.getBoardDetail(id);

        ArticleDTO findArticle = articleService.getArticle(id);

        model.addAttribute("boardName", findArticle.getBoardName());
        model.addAttribute("boardId", findArticle.getBoardId());
        model.addAttribute("articleSaveForm", new ArticleSaveForm());

        return "usr/article/write";

    }

    // model이 프론트 단으로 넘겨주는 역할
    @PostMapping("/boards/{id}/articles/write")
    public String doWrite(@Validated ArticleSaveForm articleSaveForm, BindingResult bindingResult, Model model, Principal principal, @PathVariable(name = "id") Long id) {

        if (bindingResult.hasErrors()) {

            ArticleDTO findArticle = articleService.getArticle(id);

            model.addAttribute("boardName", findArticle.getBoardName());
            model.addAttribute("boardId", findArticle.getBoardId());

            return "usr/article/write";
        }

        try {

            Member findMember = memberService.findByLoginId(principal.getName());
            Board findBoard = boardService.getBoard(articleSaveForm.getBoard_id());

            articleService.save(
                    articleSaveForm,
                    findMember,
                    findBoard
            );

        } catch (IllegalStateException e) {

            model.addAttribute("err_msg", e.getMessage());
            return "usr/article/write"; //폴더

        }

        //url
        return "redirect:/articles";
    }

    // 수정
    @GetMapping("/articles/modify/{id}")
    public String showModify(@PathVariable(name="id") Long id, Model model){
        try{
            ArticleDTO findArticle = articleService.getArticle(id);

            model.addAttribute("boardName", findArticle.getBoardName());
            model.addAttribute("boardId", findArticle.getBoardId());
            model.addAttribute("articleId", findArticle.getId());
            model.addAttribute("articleModifyForm", new ArticleModifyForm(findArticle) );

            return "usr/article/modify";

        }catch (Exception e){
            return "redirect:/articles/modify/"+id;
        }
    }

    @PostMapping("/articles/modify/{id}")
     public String doModify(@PathVariable(name="id") Long id,@Validated ArticleModifyForm articleModifyForm, BindingResult bindingResult, Principal principal, Model model){

        if(bindingResult.hasErrors()){
            ArticleDTO findArticle = articleService.getArticle(id);

            model.addAttribute("boardName", findArticle.getBoardName());
            model.addAttribute("boardId", findArticle.getBoardId());
            model.addAttribute("articleId", findArticle.getId());

            return "usr/article/modify";
        }

        try{

            ArticleDTO findArticle = articleService.getArticle(id);
            if(!findArticle.getLoginId().equals(principal.getName())){
               throw new IllegalStateException("잘못된 요청입니다.");
            }

            Board findBoard = boardService.getBoard(articleModifyForm.getBoard_id());

            articleService.modifyArticle(articleModifyForm, id, findBoard);
            return "redirect:/boards/" + id;

        } catch (Exception e){
            return "redirect:/articles/modify/"+id;
        }

    }


    // 삭제
    @GetMapping("/articles/delete/{id}")
    public String deleteArticle(@PathVariable(name="id") Long id, Principal principal){

        try {
            ArticleDTO article = articleService.getArticle(id);

            if(!article.getLoginId().equals(principal.getName())){
                return "redirect:/articles/"+id;
            }

            articleService.delete(id);
            return "redirect:/boards";

        }catch (Exception e){
            return "redirect:/";
        }

    }

    // 리스트
    @GetMapping("/articles")
    public String showList(Model model){

        List<ArticleDTO> articleList = articleService.getArticleList();

        ArticleDTO articleDTO = articleList.get(0);

        model.addAttribute("boardName", articleDTO.getBoardName());
        model.addAttribute("articleList", articleList);

        return "usr/article/list";
    }

    // 상세
    @GetMapping("/articles/{id}")
    public String showDetail(Model model, @PathVariable(name = "id") Long id){

        try {
            ArticleDTO findArticle = articleService.getArticle(id);
            model.addAttribute("article", findArticle);
            return "usr/article/detail";
        } catch (Exception e){
            return "redirect:/";
        }

    }

}