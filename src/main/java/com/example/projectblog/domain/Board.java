package com.example.projectblog.domain;

import com.example.projectblog.dto.BoardDto;

import com.example.projectblog.model.Comments;
import com.example.projectblog.model.User;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@Entity //JPA를 사용하여 데이터와 매핑하겠다는 의미
@EnableJpaAuditing //데이터를 보고 있다가 생성 또는 수정이 발생하면 자동으로 값을 넣어주는 기능
@NoArgsConstructor(access =  AccessLevel.PROTECTED)//파라미터가 없는 기본 생성자를 생성 디폴트 생성자
@AllArgsConstructor(access =  AccessLevel.PROTECTED)//모든 필드 값을 파라미터로 받는 생성자를 만듬 즉 클래스에 존재하는 모든 필드에 대한 생성자를 자동으로 생성해줍니다.
@Getter//접근 설정

public class Board extends  Timestamped{

    @Id//테이블의 주키(primary key) 역할을 한다는 것을 나타낸다.
    @GeneratedValue(strategy =  GenerationType.IDENTITY) //@GeneratedValue는 주키의 값을 위한 자동 생성 전략을 명시
    @Column(name ="board_id" )
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(20)")
    private  String title;

    @Column(name = "username", nullable = false, columnDefinition = "varchar(20)" )
    private  String username;

    @Column(nullable = false, columnDefinition = "varchar(100)" )
    private  String content;

    @JoinColumn(name = "user")
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Comments> commentsList;




    @Builder //빌더 패턴
    public Board(Long id ,String title, String username, String content, LocalDateTime modifiedAt, LocalDateTime createdAt){
        this.title = title;
        this.username = username;
        this.content = content;

    }

    public BoardDto toDto(){  //원래 도메인을 DTO로 변환
        return BoardDto.builder()
                .id(this.id)
                .title(this.title)
                .username(this.username)
                .content(this.content)
                .createdAt((this.getCreatedAt()))
                .build();
    }



}