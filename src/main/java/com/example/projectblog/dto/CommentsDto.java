package com.example.projectblog.dto;


import com.example.projectblog.domain.Board;
import com.example.projectblog.model.Comments;
import com.example.projectblog.model.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDto {


    private String comment;
    private Board board;
    private User user;





}


