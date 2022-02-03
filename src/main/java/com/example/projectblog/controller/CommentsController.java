package com.example.projectblog.controller;

import com.example.projectblog.Security.UserDetailsImpl;
import com.example.projectblog.domain.Board;
import com.example.projectblog.dto.CommentsDto;
import com.example.projectblog.model.Comments;
import com.example.projectblog.repository.CommentsRepository;
import com.example.projectblog.repository.UserRepository;
import com.example.projectblog.service.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
public class CommentsController {

    private final CommentsRepository commentsRepository;
    private final CommentsService commentsService;






}
