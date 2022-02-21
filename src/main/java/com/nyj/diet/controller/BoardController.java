package com.nyj.diet.controller;

import com.nyj.diet.domain.Board;
import com.nyj.diet.domain.Member;
import com.nyj.diet.dto.Board.BoardDTO;
import com.nyj.diet.dto.Board.BoardModifyForm;
import com.nyj.diet.dto.Board.BoardSaveForm;
import com.nyj.diet.dto.article.ArticleListDTO;
import com.nyj.diet.service.BoardService;
import com.nyj.diet.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.management.LockInfo;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final MemberService memberService;



    // 리스트
    @GetMapping("/boards")
    public String showBoardList(Model model){
        List<Board> boardList = boardService.findAll();
        
        // HTML로 보낼 수 있게 됨
        model.addAttribute("boardList", boardList);
        
        return "adm/board/list";
    }

    // 상세
    @GetMapping("/boards/{id}")
    public String showBoardDetail(@PathVariable(name = "id")Long id, Model model, @RequestParam(name = "page", defaultValue = "1") int page ){

        int size = 10;

        try{
            BoardDTO boardDetail = boardService.getBoardDetail(id);

            // 최신순으로 나오도록 하기
            List<ArticleListDTO> articleListDTO = boardDetail.getArticleListDTO();
            Collections.reverse(articleListDTO);

            // 첫번째, 마지막 index 찾기
            // 0, 10, 20, 30 ...
            int startIndex = (page - 1) * size;
            // 9, 19, 29,...
            int lastIndex = startIndex + 9;

            // 마지막 페이지
            int lastPage = (int)Math.ceil( articleListDTO.size() / (double)size) ;

            if( page == lastPage ){
                // 9보다 작을 수 있음
                lastIndex = articleListDTO.size();
            } else if ( page > lastPage ){
                return "redirect:/";
            } else { //page < lastPage
                lastIndex += 1; // subList를 사용할 때 9를 입력하면 8까지만 보여줌. 그래서 9를 보려면 10을 넣어줘야 한다.
            }

            // 페이지 자르기
            //[0 ~ 9] 10개를 보고싶으면 [0, 10]을 넣어준다.
            List<ArticleListDTO> pageInArticles = articleListDTO.subList(startIndex, lastIndex);

            model.addAttribute("board", boardDetail);
            model.addAttribute("articles", pageInArticles);
            model.addAttribute("maxPage", lastPage);
            model.addAttribute("currentPage", page);

        } catch (Exception e){
            return "redirect:/";
        }

        return "adm/board/detail";
    }
}








