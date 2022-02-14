package com.nyj.diet.dto.member;

import com.nyj.diet.domain.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MemberDTO {

    private Long memberid;

    private String loginId;
    private String name;
    private String nickname;
    private String email;

    private LocalDateTime regDate;
    private LocalDateTime updateDate;

    public MemberDTO(Member member){
        this.memberid = member.getId();

        this.loginId =member.getLoginId();
        this.name =member.getName();
        this.nickname =member.getNickname();
        this.email =member.getEmail();

        this.regDate = member.getRegDate();
        this.updateDate = member.getUpdateDate();

    }
}
