package com.example.board2.controller;

import com.example.board2.dto.Board2Dto;
import com.example.board2.entity.Board2;
import com.example.board2.repository.Board2Repository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("board2/")
public class Board2Controller {

    @Autowired
    private Board2Repository board2Repo;

    @GetMapping("list")
    private String list(Model model){
        List<Board2> board2List = board2Repo.findAll();
        model.addAttribute("board2List", board2List);
        return "main";
    }

    @GetMapping("reg")
    private String moveReg(){
        return "/board2/reg";
    }

    @PostMapping("reg")
    private String reg(Board2Dto board2Dto, Model model, HttpServletRequest req){

        Board2 board2 = Board2.builder()
                .title(board2Dto.getTitle())
                .content(board2Dto.getContent())
                .writer(board2Dto.getWriter()).build();
        HttpSession session = req.getSession();
        board2.setWriter(session.getAttribute("sessionUserName").toString());
        board2Repo.save(board2);
        model.addAttribute("msg", "게시글 등록이 완료 되었습니다.");
        return "redirect:/board2/list";
    }

    @GetMapping("detail/{no}")
    private String detail(@PathVariable Integer no, Model model){
        Board2 board2 = board2Repo.findById(no).orElse(null);
        model.addAttribute("board2", board2);
        return "board2/detail";
    }

    @GetMapping("delete")
    private String delete(@RequestParam Integer no, Model model){
        board2Repo.deleteById(no);
        model.addAttribute("msg", "삭제되었습니다.");
        return "redirect:/board2/list";
    }

    @GetMapping("/modify/{no}")
    private String modify(@PathVariable Integer no){
        Optional<Board2> board2 = board2Repo.findById(no);
        if (board2.isPresent()){
            return "/board2/modify";
        }else {
            return "redirect:/board2/list";
        }
    }

    @PostMapping("/modify/{no}")
    private String modify(@PathVariable Integer no, Board2Dto board2Dto){

        Board2 board2 = new Board2();
        board2.setNo(board2.getNo());
        board2.setTitle(board2Dto.getTitle());
        board2.setContent(board2Dto.getContent());
        board2Repo.save(board2);
        return "redirect:/board2/list"+no;
    }
}
