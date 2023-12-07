package com.example.board2.controller;

import com.example.board2.entity.Member;
import com.example.board2.dto.MemberDto;
import com.example.board2.repository.MemberRepository;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {

    @Autowired
    private MemberRepository memberRepo;

    @PostMapping("/login")
    private String login(String id, String password, Model model, HttpServletRequest request)
    {
       Member member = (Member) memberRepo.findIdAndPassword(id, password);

       if (member != null){
           HttpSession session = request.getSession();
           session.setAttribute("sessionUserName", member.getName());
           return "main";
       }else{
           model.addAttribute("msg", "아이디 또는 패스워드를 확인해주세요.");
           return "index";
       }

    }

    @GetMapping("/signup")
    private String signup(){
        return "signup";
    }

    @PostMapping("/signup")
    private String signup(MemberDto memberDto, Model model){
        if(!memberDto.getPassword1().equals(memberDto.getPassword2())){
            model.addAttribute("msg", "패스워드가 일치하지 않습니다.");
        }

        Member result= memberRepo.findById(memberDto.getId()).orElse(null);
        if(result != null){
            model.addAttribute("msg", "중복된 아이디 입니다.");
        }

        Member member = new Member();
        member.setId(memberDto.getId());
        member.setName(memberDto.getName());
        member.setPassword(memberDto.getPassword1());

        memberRepo.save(member);
        model.addAttribute("msg","회원가입이 완료 되었습니다.");

        return "index";
    }
}
