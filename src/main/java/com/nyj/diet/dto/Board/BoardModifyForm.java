package com.nyj.diet.dto.Board;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardModifyForm {
    private String name;
    private String detail;
}

