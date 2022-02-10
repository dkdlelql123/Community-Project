package com.nyj.diet.dao;

import com.nyj.diet.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

//    @Query("SELECT * FROM article WHERE board_id = :board ORDER BY id DESC LIMIT 3")
//    List<Article> findNewArticle(@Param("board") Long boardId);
}


