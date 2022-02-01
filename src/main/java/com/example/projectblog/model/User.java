package com.example.projectblog.model;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access =  AccessLevel.PROTECTED)//파라미터가 없는 기본 생성자를 생성 디폴트 생성자
@AllArgsConstructor(access =  AccessLevel.PROTECTED)//모든 필드 값을 파라미터로 받는 생성자를 만듬 즉 클래스에 존재하는 모든 필드에 대한 생성자를 자동으로 생성해줍니다.
@Entity // DB 테이블 역할을 합니다.

public class User {

    // ID가 자동으로 생성 및 증가합니다.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false 일때 중복 허용)
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String passwordCheck;

    @Column(nullable = false, unique = true)
    private String email;



    public User(String username, String password, String email, String passwordCheck) {
        this.username = username;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.email = email;

    }



}