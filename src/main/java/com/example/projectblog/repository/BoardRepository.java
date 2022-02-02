package com.example.projectblog.repository;


import com.example.projectblog.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Integer> {

    List<Board> findAllByOrderByCreatedAtDesc();
}
