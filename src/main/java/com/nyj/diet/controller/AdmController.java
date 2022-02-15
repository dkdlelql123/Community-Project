package com.nyj.diet.controller;

import com.nyj.diet.service.AdmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adm")
@RequiredArgsConstructor
public class AdmController {

    //@RequiredArgsConstructor
    //final : 초기값이 무조건 들어와야 하는데, 밑의 과정을 생략하게 해줌
    //public AdmController(){
    //    this.admController = admController;
    //}

    private final AdmService admService;

    @GetMapping("/page")
    public String showAdminPage(){
        return "adm/general/main";
    }

}
