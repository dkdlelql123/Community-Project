package com.nyj.diet.controller;

import com.nyj.diet.dto.member.MyPageDTO;
import com.nyj.diet.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;

    @GetMapping("/mypage/{loginId}")
    public String showMyPage(@PathVariable(name = "loginId") String loginId, Model model, Principal principal){

        if(!principal.getName().equals(loginId)){
            return "redirect:/";
        }

        MyPageDTO myPageDTO = memberService.getMyPageDTO(principal.getName());
        model.addAttribute("member", myPageDTO);
        model.addAttribute("loginMember", principal.getName());


        return "usr/member/mypage";
    }
}
