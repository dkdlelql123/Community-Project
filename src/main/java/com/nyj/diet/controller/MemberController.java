package com.nyj.diet.controller;

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

    // join을 위한 조직
    /**/
    @GetMapping("/members/join")
    public String showJoin(Model model ){
        // model : html으로 값을 넘겨줄 수 있음
        model.addAttribute(
                "memberSaveForm", new MemberSaveForm()
        );
        // 회원가입 했을 때 이동할 페이지 경로
        return "usr/member/join";
    }

    /**/
    @PostMapping("/member/join")
    public String doJoin(@Validated MemberSaveForm memberSaveForm, BindingResult bindingResult, Model model){
        //@Validated 유효성 검사 - 리다이랙트
        //@Validated - 꼭 같이 사용해야함함 bindingesult

        //에러값이 들어온다면 다시 회원가입 창을 보여줄거다
        if( bindingResult.hasErrors()){
            return "usr/member/join";
        }

        try{
            // 저장
            memberService.save(memberSaveForm);
        } catch (Exception e){
            // 원치 않은 상황시 - memberservice에서 메세지를 불러와 줌
            model.addAttribute("error_msg", e.getMessage());
        }

        //로그인이 성공되었을때 이동하는 페이지
        return "redirect:/";

    }

}
