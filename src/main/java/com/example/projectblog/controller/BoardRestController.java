package com.example.projectblog.controller;


import com.example.projectblog.dto.BoardDto;
import com.example.projectblog.repository.BoardRepository;
import com.example.projectblog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class BoardRestController {


    private final BoardService boardservice;
    private final BoardRepository boardRepository;


    @GetMapping("/api/board")//모든 글 조회
    public List<BoardDto> getList(){

        return boardservice.getList();
    }


    @GetMapping("/api/board/{id}") //하나의 글 조회
    public  BoardDto getOne(@PathVariable Long id){
        return boardservice.getOne(id);
    }


    @PostMapping("/api/board") //글 작성
    public BoardDto create(@RequestBody BoardDto createBoard) {

        return boardservice.create(createBoard);
    }


}
