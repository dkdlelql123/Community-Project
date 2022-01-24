package com.nyj.diet.controller;

import com.nyj.diet.domain.Board;
import com.nyj.diet.dto.Board.BoardDTO;
import com.nyj.diet.dto.Board.BoardModifyForm;
import com.nyj.diet.dto.Board.BoardSaveForm;
import com.nyj.diet.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.management.LockInfo;
import java.util.List;

@Controller
@RequestMapping("/adm")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    //생성
    @GetMapping("/boards/add")
    public String showAddBoard(Model model){
        model.addAttribute("boardSaveForm", new BoardSaveForm());

        return "adm/board/add";
    }

    @PostMapping("/boards/add")
    public String doAddBoard(BoardSaveForm boardSaveForm){
        boardService.save(boardSaveForm);

        return "redirect:/";
    }

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
    public String showBoardDetail(@PathVariable(name = "id")Long id, Model model){

        try{
            BoardDTO boardDetail = boardService.getBoardDetail(id);
            model.addAttribute("board", boardDetail);

        } catch (Exception e){
            return "redirect:/";
        }

        return "adm/board/detail";
    }

    // 수정
    @GetMapping("/boards/modify")
    public String showModifyBoard(Model model){
        model.addAttribute("boardModifyForm", new BoardModifyForm() );

        return "adm/board/modify";
    }

    @PostMapping("/boards/modify")
    public String doModifyBoard(BoardModifyForm boardModifyForm){

        try {
            boardService.modify(boardModifyForm);
        } catch (Exception e){
            return "adm/board/modify";
        }

        return "redirect:/";
    }

    // 삭제
    @GetMapping("/boards/delete/{id}")
    public String doDeleteBoard(@PathVariable(name="id") Long id){

        try{
            boardService.delete(id);
            return "adm/board/list";

        } catch (Exception e){
            return "adm/board/list";
        }

    }
}








