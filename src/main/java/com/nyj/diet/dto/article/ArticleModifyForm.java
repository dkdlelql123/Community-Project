package com.nyj.diet.dto.article;

import lombok.AllArgsConstructor;
import lombok.Data;

// 어떤 인자를 받는 생성자로 만들어주겠다.

@Data
@AllArgsConstructor
public class ArticleModifyForm {
    private String title;
    private String body;
}
