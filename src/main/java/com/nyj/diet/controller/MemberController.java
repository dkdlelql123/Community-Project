package com.nyj.diet.controller;

import com.nyj.diet.dto.MemberLoginForm;
import com.nyj.diet.dto.MemberSaveForm;
import com.nyj.diet.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/join")
    public String showJoin(Model model) {

        model.addAttribute("memberSaveForm", new MemberSaveForm());
        return "usr/member/join";
    }

    // Validated 유효성 검사 - 리다이랙트
    // Validated - 꼭 같이 사용해야함함 bindingesult
    @PostMapping("/members/join")
    public String doJoin(@Validated MemberSaveForm memberSaveForm, BindingResult bindingResult, Model model){

        //에러값이 들어온다면 다시 회원가입 창을 보여줄거다
        if ( bindingResult.hasErrors() ) {
            return "usr/member/join";
        }

        try{
            // 저장
            memberService.save(memberSaveForm);
        } catch (Exception e){
            // 원치 않은 상황시 - memberservice에서 메세지를 불러와 줌
            model.addAttribute("err_msg", e.getMessage());

            return "usr/member/signup";
        }

        //로그인이 성공되었을때 이동하는 페이지
        return "redirect:/";
    }


    // 로그인
    @GetMapping("/members/login")
    public String showLogin(Model model){

        model.addAttribute("memberLoginForm", new MemberLoginForm());

        return "usr/member/login";
    }

}
