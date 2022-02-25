package com.nyj.diet.service;

import com.nyj.diet.domain.Member;
import com.nyj.diet.dto.member.FindPassWordForm;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailService {

    private final MemberService memberService;
    private final JavaMailSender javaMailSender;
    private static final String FROM_ADDRESS = "dkdlelq123@gmail.com";

    public void sendMail(FindPassWordForm findPassWordForm){


        Member findMember = memberService.findByLoginId(findPassWordForm.getLoginId());

        String uuid = UUID.randomUUID().toString();
        String tempPw = uuid.substring(0, 5);

        memberService.changeTempPw(tempPw, findMember);

        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper mailHelper = new MimeMessageHelper(mail, true, "UTF-8");

            mailHelper.setFrom(FROM_ADDRESS);
            mailHelper.setTo(findPassWordForm.getEmail());
            mailHelper.setSubject("임시 비밀번호를 보내드립니다.");
            mailHelper.setText(findMember.getNickname() + "님의 임시 비밀번호는" + tempPw + "입니다.\r\n 로그인 후 비밀번호를 변경해 주시기 바랍니다.");

            javaMailSender.send(mail);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
