package com.example.board2.repository;

import com.example.board2.entity.Board2;
import com.example.board2.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface Board2Repository extends JpaRepository<Board2, Integer> {

}
