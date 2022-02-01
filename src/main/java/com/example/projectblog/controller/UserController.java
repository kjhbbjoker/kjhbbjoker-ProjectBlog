package com.example.projectblog.controller;

import com.example.projectblog.dto.SignDto;
import com.example.projectblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller

public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원 로그인 페이지
    @GetMapping("/user/login")
    public String login() {

        return "login";
    }


    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup() {
        return "signup";
    }



    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(@Valid @ModelAttribute SignDto requestDto, BindingResult bindingResult) {
        System.out.println(requestDto);

      /* if(bindingResult.hasErrors()){
            return "signup";
        }*/
        userService.registerUser(requestDto, bindingResult); //dto변환
        return "login";



    }
}
