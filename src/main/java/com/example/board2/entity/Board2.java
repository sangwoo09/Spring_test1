package com.example.board2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Timestamp;

@Entity
@Data
@Builder
@NoArgsConstructor //아무것도 없는 기본 생성자 작성
@AllArgsConstructor //전부 있는 기본 생성자 작성
public class Board2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;
    private String title;
    private String content;
    private String writer;
    @Column(name = "reg_date", insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp regDate;
    @Column(insertable = false)
    @ColumnDefault("0")
    private int count;
}
