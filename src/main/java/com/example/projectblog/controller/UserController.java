package com.example.projectblog.controller;

import com.example.projectblog.dto.SignDto;
import com.example.projectblog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        userService.kakaoLogin(code);
        return "redirect:/";
    }


    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup() {
        return "signup";
    }



    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(Model model, @Valid @ModelAttribute SignDto requestDto, BindingResult bindingResult) {



    if(bindingResult.hasErrors()){
        System.out.println(requestDto);
            return "signup"; //에러 메시지를 줘야 하는데 어떻게 객체를 전달해야되는지 몰라서 일단...잘못되면 회원가입 창 리턴 받게 작성했습니다.
        }
        userService.registerUser(requestDto, bindingResult); //dto변환
        return "login";








    }

}
