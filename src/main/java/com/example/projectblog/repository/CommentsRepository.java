package com.example.projectblog.repository;

import com.example.projectblog.domain.Board;
import com.example.projectblog.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentsRepository extends JpaRepository<Comments, Long> {


    List<Comments> findByBoardId(Long boardId);
}