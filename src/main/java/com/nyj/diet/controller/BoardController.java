package com.nyj.diet.controller;

import com.nyj.diet.domain.Board;
import com.nyj.diet.dto.Board.BoardDTO;
import com.nyj.diet.dto.article.ArticleListDTO;
import com.nyj.diet.service.BoardService;
import com.nyj.diet.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final MemberService memberService;


    // 리스트
    @GetMapping("/boards")
    public String showBoardList(Model model) {
        List<Board> boardList = boardService.findAll();

        // HTML로 보낼 수 있게 됨
        model.addAttribute("boardList", boardList);

        return "adm/board/list";
    }

    // 상세
    @GetMapping("/boards/{id}")
    public String showBoardDetail(@PathVariable(name = "id") Long id, Model model, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "search", defaultValue = "") String search) {

        int size = 10;

        try {
            BoardDTO boardDetail = boardService.getBoardDetail(id);

            List<ArticleListDTO> articleListDTO = boardDetail.getArticleListDTO();

            List<ArticleListDTO> store = new ArrayList<>();


            for (ArticleListDTO listDTO : articleListDTO) {
                if (listDTO.getTitle().contains(search)) store.add(listDTO);
            }


            if (store.size() != 0) {
                articleListDTO = store;
            }

            Collections.reverse(articleListDTO);

            int startIndex = (page - 1) * size;
            int lastIndex = (page * size) - 1;
            int lastPage = (int) Math.ceil(articleListDTO.size() / (double) size);

            if (page == lastPage) {
                lastIndex = articleListDTO.size();
            } else if (page > lastPage) {
                return "redirect:/";
            } else { //page < lastPage
                lastIndex += 1; // subList를 사용할 때 9를 입력하면 8까지만 보여줌. 그래서 9를 보려면 10을 넣어줘야 한다.
            }

            // 페이지 자르기
            //[0 ~ 9] 10개를 보고싶으면 [0, 10]을 넣어준다.
            List<ArticleListDTO> pageInArticles = articleListDTO.subList(startIndex, lastIndex);

            if (!search.equals("") && store.size() == 0) {
                pageInArticles = store;
            }

            model.addAttribute("board", boardDetail);
            model.addAttribute("articles", pageInArticles);
            model.addAttribute("maxPage", lastPage);
            model.addAttribute("currentPage", page);
            model.addAttribute("search", search);

        } catch (Exception e) {
            return "redirect:/";
        }

        return "adm/board/detail";
    }
}








