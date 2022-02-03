package com.nyj.diet.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

//어노테이션
//db table 1:1연결
//무분별한 객체 생성을 막기 위해? AccessLevel.PROTECTED
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String body;

    //여기서부터 회원 연결
    // ManyToOne ?
    // fetch = FetchType.LAZY ?


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private LocalDateTime regDate = LocalDateTime.now();
    private LocalDateTime updateDate = LocalDateTime.now();


    // 생성 메소드
    public static Article createArticle( String title, String body ) {
        Article article = new Article();

        article.title = title;
        article.body = body;

        return article;
    }

    // 수정
    public void modifyArticle(String title, String body ){
        this.title = title;
        this.body = body;
        this.updateDate = LocalDateTime.now();
    }


    //  연관관계 메소드
    public void setMember(Member member) {

        this.member = member;
        member.getArticles().add(this);

    }

    // 연관관계 메소드
    public void setBoard(Board board){
        this.board = board;
        board.getArticles().add(this);
    }
}
