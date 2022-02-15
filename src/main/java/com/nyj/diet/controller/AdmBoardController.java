package com.nyj.diet.controller;

import com.nyj.diet.domain.Member;
import com.nyj.diet.dto.Board.BoardDTO;
import com.nyj.diet.dto.Board.BoardModifyForm;
import com.nyj.diet.dto.Board.BoardSaveForm;
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
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/adm")
@RequiredArgsConstructor
public class AdmBoardController {

    private final BoardService boardService;
    private final MemberService memberService;

    // 작업해야할 것
    // 저장, 수정, 삭제, 리스트


    // 리스트
    @GetMapping("/boards")
    public String showManageBoards(Model model){

        model.addAttribute("boards",boardService.getBoardList());

        return "adm/board/main";
    }

    // 저장
    @GetMapping("/boards/add")
    public String showAddBoard(Model model){
        model.addAttribute("boardSaveForm", new BoardSaveForm());

        return "adm/board/add";
    }
    
    @PostMapping("/boards/add")
    public String doAddBoard(@Validated BoardSaveForm boardSaveForm, BindingResult bindingResult, Principal principal){

        if(bindingResult.hasErrors()){
            return "adm/board/add";
        }

        Member findAdmin = memberService.findByLoginId(principal.getName());

        boardService.save(boardSaveForm, findAdmin);

        // redirect url주소로 넘겨준다.
        return "redirect:/adm/boards";
    }

    // 수정
    @GetMapping("/boards/modify/{id}")
    public String showModifyBoard(@PathVariable(name = "id")Long id, Model model){

        try{
            BoardDTO board = boardService.getBoardDetail(id);

            model.addAttribute("boardId", board.getId());
            model.addAttribute("boardModifyForm", new BoardModifyForm(
                    board.getId(),
                    board.getName(),
                    board.getDetail()
            ));

            return "adm/board/modify";

        }catch(Exception e){
            return "redirect:/adm/boards";
        }

    }

    @PostMapping("/boards/modify/{id}")
    public String doModifyBoard(@PathVariable(name = "id") Long id,@Validated BoardModifyForm boardModifyForm ,BindingResult bindingResult, Model model){

        BoardDTO findBoard = boardService.getBoardDetail(id);

        if(bindingResult.hasErrors()){
            model.addAttribute("boardId", findBoard.getId());
            return "adm/board/modify";
        }

        try {
            boardService.modify(id, boardModifyForm);
            return "redirect:/adm/boards";
        } catch (Exception e){
            return "adm/board/modify";
        }

    }

    // 삭제
    @GetMapping("/boards/delete/{id}")
    public String doDeleteBoard(@PathVariable(name="id") Long id){

        try{
            boardService.delete(id);
            return "redirect:/adm/boards";

        } catch (Exception e){
            return "adm/board/list";
        }

    }
}
