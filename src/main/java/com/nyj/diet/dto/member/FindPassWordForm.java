package com.nyj.diet.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//data - getter, setter 사용가능
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindPassWordForm {
    private String loginId;
    private String email;

}
