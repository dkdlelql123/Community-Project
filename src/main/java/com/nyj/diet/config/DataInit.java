package com.nyj.diet.config;

import com.nyj.diet.dao.ArticleRepository;
import com.nyj.diet.dao.BoardRepository;
import com.nyj.diet.dao.MemberRepository;
import com.nyj.diet.domain.Article;
import com.nyj.diet.domain.Board;
import com.nyj.diet.domain.Member;
import com.nyj.diet.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

// 스프링부트
// component, bean 차이점 알아보기
@Component
@RequiredArgsConstructor
public class DataInit {

    private final InitService initService;

    // 서버를 (재)시작 할때 postConstruct 먼저 실행해줌.
    @PostConstruct
    public void init() {
        initService.initAdmin();
        initService.initMember();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final MemberRepository memberRepository;
        private final BoardRepository boardRepository;
        private final ArticleRepository articleRepository;

        //메소드 생성 - 관리자, 게시판 생성
        public void initAdmin() {
            // 비밀번호 encoder
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            Member admin = Member.createMember(
                    "admin",
                    bCryptPasswordEncoder.encode("admin"),
                    "관리자",
                    "관리자",
                    "admin@admin.com",
                    Role.ADMIN
            );

            memberRepository.save(admin);

            for (int i = 0; i < 3; i++) {
                Board board = Board.createBoard(
                        i + "번째 게시판",
                        "게시판입니다",
                        admin
                );
                boardRepository.save(board);
            }
        }

        // user 생성, 게시글 생성
        public void initMember() {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            List<Board> boardList = boardRepository.findAll();

            int count = 0;
            for (int i = 1; i <= 5; i++) {
                Member user = Member.createMember(
                        "user" + i,
                        bCryptPasswordEncoder.encode("user" + i),
                        "user" + i,
                        "user" + i,
                        "user" + i + "@user.com",
                        Role.MEMBER
                );
                memberRepository.save(user);

                for (int j = 0; j < 3; j++) {
                    count++;
//                  for (int a = 0; a < 3; a++) {
                    Article article = Article.createArticle(
                            count +"번째 게시물입니다" ,
                            "아 정말 정말 백수가 적성에 맞습니다 ^^"
                    );
                    article.setMember(user);
                    article.setBoard(boardList.get(j));
                    articleRepository.save(article);
//                  }
                }

            }

        }

        // 다음

    }

}
