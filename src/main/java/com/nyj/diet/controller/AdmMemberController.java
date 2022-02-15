package com.nyj.diet.controller;

import com.nyj.diet.dto.member.MemberDTO;
import com.nyj.diet.dto.member.MemberDetailDTO;
import com.nyj.diet.service.AdmMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/adm")
@RequiredArgsConstructor
public class AdmMemberController {

    private final AdmMemberService admMemberService;

    @GetMapping("/members/list")
    public String showManageMember(Model model){

        List<MemberDTO> members = admMemberService.getMemberList();
        model.addAttribute("members", members);

        return "adm/member/main";
    }

    @GetMapping("/members/detail/{id}")
    public String showMemberDetail(@PathVariable(name = "id") Long id, Model model){

        MemberDetailDTO memberDetail = admMemberService.getMemberDetail(id);
        model.addAttribute("member",memberDetail);

        return "adm/member/detail";
    }

    @GetMapping("/members/ban/{id}")
    public String banMember(@PathVariable(name = "id") Long id){
        admMemberService.banMember(id);

        return "redirect:/adm/members/list";
    }



}
