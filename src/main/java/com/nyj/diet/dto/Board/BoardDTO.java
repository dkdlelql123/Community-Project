package com.nyj.diet.dto.Board;

import com.nyj.diet.domain.Board;
import com.nyj.diet.dto.article.ArticleListDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BoardDTO {
    private Long id;

    private String name;
    private String detail;

    private List<ArticleListDTO> articleListDTO;

    private LocalDateTime reg_date;
    private LocalDateTime update_date;

    public BoardDTO(Board board, List<ArticleListDTO> articleListDTO){
        this.id = board.getId();
        this.name = board.getName();
        this.detail = board.getDetail();
        this.articleListDTO = articleListDTO;
        this.reg_date = board.getReg_date();
        this.update_date = board.getUpdate_date();
    }

}
