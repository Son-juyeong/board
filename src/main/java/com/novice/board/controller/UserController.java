package com.novice.board.controller;

import com.novice.board.domain.User;
import com.novice.board.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping("/register")
    public String registerForm(){
        return "user/registerForm";
    }

    @GetMapping("/register-complete")
    public String registerComplete(){
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user){
        userService.register(user);
        return "redirect:/register-complete";
    }

    @GetMapping("/login")
    public String loginForm(Model model){
        return "user/login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpServletRequest request,
                        RedirectAttributes redirectAttributes){
        Optional<User> user = userService.login(username, password);
        boolean status = user.isPresent();
        redirectAttributes.addFlashAttribute("status", status);
        if(status){
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", user.get().getUserid());
            return "redirect:/posts";
        } else return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session!=null)session.invalidate();
        return "redirect:/posts";
    }

    @GetMapping("/posts")
    public String getPosts(HttpSession session, Model model){
        model.addAttribute("isLogin", session.getAttribute("userId")!=null);
        return "board/list";
    }
}
