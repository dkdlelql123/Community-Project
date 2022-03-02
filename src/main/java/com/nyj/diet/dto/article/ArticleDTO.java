package com.nyj.diet.dto.article;

import com.nyj.diet.domain.Article;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleDTO {
    private Long id;

    private String title;
    private String body;

    private String loginId;
    private String authorName;

    private Long boardId;
    private String boardName;

    private LocalDateTime reg_date;
    private LocalDateTime update_date;

    private int hit;

    public ArticleDTO(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.body= article.getBody();

        this.loginId=article.getMember().getLoginId();
        this.authorName=article.getMember().getLoginId();

        this.boardId=article.getBoard().getId();
        this.boardName=article.getBoard().getName();

        this.reg_date=article.getRegDate();
        this.update_date = article.getUpdateDate();

        this.hit = article.getHit();
    }
}
