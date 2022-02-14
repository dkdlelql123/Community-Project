package com.nyj.diet.controller;

import com.nyj.diet.dto.member.MemberDTO;
import com.nyj.diet.service.AdmMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/adm")
@RequiredArgsConstructor
public class AdmMemberController {

    private final AdmMemberService admMemberService;

    @GetMapping("/members")
    public String showManageMember(Model model){

        List<MemberDTO> members = admMemberService.getMemberList();
        model.addAttribute("members", members);

        return "adm/general/page";
    }


}
