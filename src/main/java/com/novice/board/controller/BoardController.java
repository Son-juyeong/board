package com.novice.board.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
    @GetMapping("/posts")
    public String getPosts(HttpSession session, Model model){
        model.addAttribute("isLogin", session.getAttribute("userId")!=null);
        return "board/list";
    }
}
