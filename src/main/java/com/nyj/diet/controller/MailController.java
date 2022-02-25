package com.nyj.diet.controller;

import com.nyj.diet.dto.member.FindPassWordForm;
import com.nyj.diet.service.MailService;
import com.nyj.diet.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;
    private final MemberService memberService;

    @ResponseBody
    @PostMapping("/mails/find/pw")
    public boolean getForgotPassword(@RequestBody FindPassWordForm findPassWordForm){

        if (!memberService.isDupleLoginId(findPassWordForm.getLoginId())){
            return false;
        }

        try {
            mailService.sendMail(findPassWordForm);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
