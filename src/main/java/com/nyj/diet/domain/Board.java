package com.nyj.diet.domain;

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

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Article> articles = new ArrayList<>();

    public static Board createBoard(String name, String detail){
        Board board = new Board();

        board.name = name;
        board.detail = detail;

        return board;
    }

    public void modifyBoard(String name, String detail){
        this.name = name;
        this.detail = detail;

        this.update_date = LocalDateTime.now();
    }
}
