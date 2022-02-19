package com.nyj.diet.dto.article;

import com.nyj.diet.domain.Article;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleListDTO {
    private Long id;

    private String title;
    private String nickname;

    private LocalDateTime reg_date;
    private LocalDateTime update_date;

    private int hit;

    public ArticleListDTO(Article article){
        // dto article @getter 어노테이션이 있어야 가져올 수 있다
        // privcate Long id에 article.getId 넣어줌
        this.id = article.getId();
        this.title = article.getTitle();
        this.nickname = article.getMember().getNickname();
        this.reg_date = article.getRegDate();
        this.update_date = article.getUpdateDate();
        this.hit = article.getHit();
    }
}
