package com.nyj.diet.dto.Board;

import com.nyj.diet.domain.Board;
import lombok.Data;

@Data
public class BoardListDTO {
    private Long boardId;

    private String name;
    private String detail;

    public BoardListDTO(Board board){
        this.boardId = board.getId();

        this.name = board.getName();
        this.detail = board.getDetail();
    }

}
