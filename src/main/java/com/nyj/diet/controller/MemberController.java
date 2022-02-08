package com.nyj.diet.controller;

import com.nyj.diet.domain.Member;
import com.nyj.diet.dto.member.MemberLoginForm;
import com.nyj.diet.dto.member.MemberModifyForm;
import com.nyj.diet.dto.member.MemberSaveForm;
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
    public String doJoin(@Validated MemberSaveForm memberSaveForm, BindingResult bindingResult, Model model) {

        //에러값이 들어온다면 다시 회원가입 창을 보여줄거다
        if (bindingResult.hasErrors()) {
            return "usr/member/join";
        }

        try {
            // 저장
            memberService.save(memberSaveForm);
        } catch (Exception e) {
            // 원치 않은 상황시 - memberservice에서 메세지를 불러와 줌
            model.addAttribute("err_msg", e.getMessage());

            return "usr/member/signup";
        }

        //로그인이 성공되었을때 이동하는 페이지
        return "redirect:/";
    }


    // 로그인
    @GetMapping("/members/login")
    public String showLogin(Model model) {

        model.addAttribute("memberLoginForm", new MemberLoginForm());

        return "usr/member/login";
    }

    // 회원정보 수정
    @GetMapping("/members/modify/{id}")
    public String showModify(@PathVariable(name="id") Long id, Model model, Principal principal) {

        Member findMember = memberService.findById(id);

        if(!findMember.getLoginId().equals(principal.getName())){
            return "redirect:/";
        }
        model.addAttribute("memberModifyForm", new MemberModifyForm(findMember));

        return "usr/member/modify";
    }

    @PostMapping("/members/modify/{id}")
    public String doModify(@PathVariable(name="id") Long id, @Validated MemberModifyForm memberModifyForm, BindingResult bindingResult, Model model, Principal principal) {

        // validated > error 시 bindingResult
        if(bindingResult.hasErrors()){
            return "usr/member/modify";
        }

        Member findMember = memberService.findById(id);

        if( !principal.getName().equals(findMember.getLoginId())){
            return "redirect:/";
        }

        try {
            memberService.modifyMember(memberModifyForm, principal.getName());
        } catch (Exception e) {
            model.addAttribute("err_msg", e.getMessage());
            model.addAttribute("memberModifyForm", new MemberModifyForm(findMember));
            return "redirect:/";
        }

        return "redirect:/members/modify/"+id;
    }
}
