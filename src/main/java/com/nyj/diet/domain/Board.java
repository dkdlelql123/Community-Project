package com.nyj.diet.dto.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Board {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String detail;

    private LocalDateTime reg_date = LocalDateTime.now();
    private LocalDateTime update_date = LocalDateTime.now();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Article> articles = new ArrayList<>();

    public static Board createBoard(String name, String detail, Member member) {

        Board board = new Board();

        board.name = name;
        board.detail = detail;

        board.member = member;

        return board;

    }

    public void modifyBoard(String name, String detail){

        this.name = name;
        this.detail = detail;

        this.update_date = LocalDateTime.now();
    }

}
