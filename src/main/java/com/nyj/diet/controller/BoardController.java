package com.nyj.diet.controller;

import com.nyj.diet.domain.Board;
import com.nyj.diet.domain.Member;
import com.nyj.diet.dto.Board.BoardDTO;
import com.nyj.diet.dto.Board.BoardModifyForm;
import com.nyj.diet.dto.Board.BoardSaveForm;
import com.nyj.diet.service.BoardService;
import com.nyj.diet.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.management.LockInfo;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final MemberService memberService;

    //생성
    @GetMapping("/adm/boards/add")
    public String showAddBoard(Model model){
        model.addAttribute("boardSaveForm", new BoardSaveForm());

        return "adm/board/add";
    }

    // 생성
    @PostMapping("/adm/boards/add")
    public String doAddBoard(BoardSaveForm boardSaveForm, Principal principal){

        Member findAdmin = memberService.findByLoginId(principal.getName());

        boardService.save(boardSaveForm, findAdmin);

        // redirect url주소로 넘겨준다.
        return "redirect:/adm/boards";
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
    @GetMapping("/adm/boards/modify/{id}")
    public String showModifyBoard(@PathVariable(name = "id")Long id,Model model){

        try{
            BoardDTO board = boardService.getBoardDetail(id);

            model.addAttribute("board", new BoardModifyForm(
                    board.getId(),
                    board.getName(),
                    board.getDetail()
            ));

            return "adm/board/modify";

        }catch(Exception e){
            return "redirect:/";
        }

    }

    @PostMapping("/adm/boards/modify/{id}")
    public String doModifyBoard(@PathVariable(name = "id") Long id,BoardModifyForm boardModifyForm){

        try {
            boardService.modify(id, boardModifyForm);
            return "redirect:/adm/boards";
        } catch (Exception e){
            return "adm/board/modify";
        }

    }

    // 삭제
    @GetMapping("/adm/boards/delete/{id}")
    public String doDeleteBoard(@PathVariable(name="id") Long id){

        try{
            boardService.delete(id);
            return "redirect:/adm/boards";

        } catch (Exception e){
            return "adm/board/list";
        }

    }
}








