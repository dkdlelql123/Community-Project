package com.nyj.diet.service;

import com.nyj.diet.dao.ArticleRepository;
import com.nyj.diet.domain.Article;
import com.nyj.diet.domain.Member;
import com.nyj.diet.dto.article.ArticleSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {

    // 서비스는 리파지토리랑만 소통이 가능하다.
    private final ArticleRepository articleRepository;

    // 게시물 저장 로직
    public void save(ArticleSaveForm articleSaveForm, Member member){

        // 게시물 만듬
        Article article = Article.createArticle(
                articleSaveForm.getTitle(),
                articleSaveForm.getBody()
        );
        
        // 게시물 멤버 추가
        article.setMember(member);
        
        // db 추가
        articleRepository.save(article);

    }
    

}
