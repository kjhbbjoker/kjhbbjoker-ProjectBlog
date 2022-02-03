package com.example.projectblog.service;
import com.example.projectblog.domain.Board;
import com.example.projectblog.dto.BoardDto;
import com.example.projectblog.dto.CommentsDto;
import com.example.projectblog.dto.SignDto;
import com.example.projectblog.model.Comments;
import com.example.projectblog.model.User;
import com.example.projectblog.repository.BoardRepository;
import com.example.projectblog.repository.CommentsRepository;

import com.example.projectblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CommentsService {

    private final CommentsRepository commentsRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;



    public List<Comments> findAllComments(Long boardId){ // 게시글 아이디에 연관 된 댓글을 불러옴
        List<Comments> comments = commentsRepository.findByBoardId(boardId);

        return comments;
    }


    public void updateComment(Comments comments, CommentsDto commentsDto){
        comments.updateComment(commentsDto);
    }




    /** 댓글을 삭제할 수 있는 deleteComment 입니다. */
    public void deleteComment(Comments comment){
        commentsRepository.delete(comment);
    }









}








