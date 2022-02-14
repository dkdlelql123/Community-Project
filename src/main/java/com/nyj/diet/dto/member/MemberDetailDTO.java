package com.nyj.diet.dto.member;

import com.nyj.diet.domain.Member;
import com.nyj.diet.dto.article.ArticleListDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class MemberDetailDTO {

    private Long memberId;

    private String loginId;
    private String name;
    private String nickname;

    private LocalDateTime regDate;

    private List<ArticleListDTO> articles;

    public MemberDetailDTO(Member member, List<ArticleListDTO> articles){
        this.memberId = member.getId();

        this.loginId =member.getLoginId();
        this.name =member.getName();
        this.nickname =member.getNickname();

        this.regDate = member.getRegDate();

        this.articles = articles;

    }
}
