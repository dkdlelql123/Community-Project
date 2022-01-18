package com.nyj.diet.dto.article;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ArticleSaveForm {
    @NotBlank //유효성 검사를 위해
    private String title;

    @NotBlank
    private String body;

}
