package com.nyj.diet.dto.member;

import com.nyj.diet.domain.Member;
import com.nyj.diet.dto.article.ArticleDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MyPageDTO {

    private String loginId;
    private String nickname;
    private String name;

    private List<ArticleDTO> articles;

    public MyPageDTO(Member member, List<ArticleDTO> articleList){
        this.loginId = member.getLoginId();
        this.nickname = member.getNickname();
        this.name = member.getName();

        this.articles = articleList;
    }
}
