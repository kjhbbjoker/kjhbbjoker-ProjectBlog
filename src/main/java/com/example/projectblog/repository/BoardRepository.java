package com.example.projectblog.repository;


import com.example.projectblog.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {

    List<Board> findAllByOrderByCreatedAtDesc();
}
