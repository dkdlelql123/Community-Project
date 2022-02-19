package com.nyj.diet.service;

import com.nyj.diet.dao.ArticleRepository;
import com.nyj.diet.domain.Article;
import com.nyj.diet.domain.Board;
import com.nyj.diet.domain.Member;
import com.nyj.diet.dto.article.ArticleDTO;
import com.nyj.diet.dto.article.ArticleModifyForm;
import com.nyj.diet.dto.article.ArticleSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {

    // 서비스는 리파지토리랑만 소통이 가능하다.
    private final ArticleRepository articleRepository;

    // 게시물 저장 로직
    @Transactional
    public void save(ArticleSaveForm articleSaveForm, Member member, Board board) {

        Article article = Article.createArticle(
                articleSaveForm.getTitle(),
                articleSaveForm.getBody()
        );

        // 게시물 멤버 추가
        article.setMember(member);
        article.setBoard(board);

        // db 추가
        articleRepository.save(article);

    }

    public ArticleDTO getArticle(Long id) throws NoSuchElementException{
        Article article = getById(id);

        ArticleDTO articleDTO = new ArticleDTO(article);

        return articleDTO;
    }

    // 수정
    public Optional<Article> findById(Long id){
        return articleRepository.findById(id);
    }


    public Article getById(Long id) throws NoSuchElementException{

        Optional<Article> articleOptional = findById(id);

        articleOptional.orElseThrow(
                () -> new NoSuchElementException("존재하는 게시물을 찾을 수 없습니다.")
        );

        return articleOptional.get();
    }

    @Transactional
    public void modifyArticle(ArticleModifyForm articleModifyForm, Long id, Board board){
        Article findArticle = getById(id);

        findArticle.modifyArticle(
            articleModifyForm.getTitle(),
            articleModifyForm.getBody()
        );

        findArticle.setBoard(board);
    }

    // 리스트
    public List<ArticleDTO> getArticleList() {
        List<Article> articleList = articleRepository.findAll();

        List<ArticleDTO> articleDTOList = new ArrayList<>();

        for(Article article : articleList){
           ArticleDTO articleDTO = new ArticleDTO(article);
           articleDTOList.add(articleDTO);
        }

        return articleDTOList;
    }

    // 삭제 - DB 영향을 끼치므로 Transactional 붙인다.
    @Transactional
    public void delete(Long id){
        Article findArticle = getById(id);
        articleRepository.delete(findArticle);
    }

    // hit 증가
    @Transactional
    public void hitUp(Long id){
        Article findArticle = getById(id);

        findArticle.hitUp();
    }
}
