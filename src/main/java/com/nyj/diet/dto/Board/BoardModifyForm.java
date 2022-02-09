package com.nyj.diet.dto.Board;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class BoardModifyForm {
    private Long id;

    @NotBlank(message = "게시판 이름을 넣어주세요.")
    private String name;

    private String detail;
}

