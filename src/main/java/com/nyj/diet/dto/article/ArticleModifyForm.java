package com.nyj.diet.dto.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

// 어떤 인자를 받는 생성자로 만들어주겠다.

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleModifyForm {
    
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    
    @NotBlank(message = "내용을 입력해주세요.")
    private String body;

    private Long board_id;

    private String board_name;

    public ArticleModifyForm(ArticleDTO articleDTO){
        this.title = articleDTO.getTitle();
        this.body = articleDTO.getBody();

        this.board_id = articleDTO.getBoardId();
    }
}
