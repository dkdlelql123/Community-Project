package com.nyj.diet.service;

import com.nyj.diet.dao.ArticleRepository;
import com.nyj.diet.domain.Article;
import com.nyj.diet.dto.article.ArticleListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdmArticleService {

    private final ArticleRepository articleRepository;

    public List<ArticleListDTO> getArticleList(){

        List<ArticleListDTO> articleListDTO = new ArrayList<>();

        List<Article> articles = articleRepository.findAll();

        for(Article article : articles){
            ArticleListDTO articleDTO = new ArticleListDTO(article);
            articleListDTO.add(articleDTO);
        }

        return articleListDTO;
    }

    @Transactional
    public void deleteArticle(Long id){
        Optional<Article> findArticle = articleRepository.findById(id);

        findArticle.orElseThrow(
                ()-> new IllegalStateException("존재하지 않는 게시물입니다.")
        );

        articleRepository.delete(findArticle.get());

    }

}
