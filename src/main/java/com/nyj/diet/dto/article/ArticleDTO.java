package com.nyj.diet.dto.article;

import com.nyj.diet.domain.Article;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleDTO {
    private Long id;

    private String title;
    private String body;

    private String authorName;

    private String boardName;

    private LocalDateTime reg_date;
    private LocalDateTime update_date;

    public ArticleDTO(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.body= article.getBody();
        this.authorName=article.getMember().getNickname();
        this.boardName=article.getBoard().getName();
        
        this.reg_date=article.getRegDate();
        this.update_date = article.getUpdateDate();
    }
}