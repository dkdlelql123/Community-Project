package com.nyj.diet.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindPassWordForm {

//    data - getter, setter 사용가능
    private String loginId;

    private String email;

}
