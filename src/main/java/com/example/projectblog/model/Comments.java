package com.example.projectblog.model;

import com.example.projectblog.domain.Board;
import com.example.projectblog.domain.Timestamped;
import com.example.projectblog.dto.BoardDto;
import com.example.projectblog.dto.CommentsDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.xml.sax.ext.LexicalHandler;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class Comments extends Timestamped {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String comment;  //댓글 내용

    @ManyToOne
    @JoinColumn(name = "board")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;








}