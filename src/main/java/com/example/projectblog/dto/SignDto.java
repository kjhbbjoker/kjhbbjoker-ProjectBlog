package com.example.projectblog.dto;

import com.example.projectblog.model.User;
import com.sun.istack.NotNull;
import lombok.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@Data
@NoArgsConstructor//파라미터가 없는 기본 생성자를 생성 디폴트 생성자
@AllArgsConstructor//모든 필드 값을 파라미터로 받는 생성자를 만듬 즉 클래스에 존재하는 모든 필드에 대한 생성자를 자동으로 생성해줍니다.


public class SignDto {

    private Long id;

    @NotBlank(message = "아이디를 입력해주세요")
    @Pattern(regexp = "^[a-zA-Z0-9]{3,15}$", message = "아이디를 3~15자로 입력해주세요. (특수문자는 x)")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,15}$", message = "비밀번호를 4~15자로 입력해주세요. (특수문자는 x)")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,15}$", message = "비밀번호를 4~15자로 입력해주세요. (특수문자는 x)")
    private String passwordCheck;

    @NotBlank(message = "이메일을 입력해주세요")
    @Email
    private String email;


    private boolean admin = false;
    private String adminToken = "";






}