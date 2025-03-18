package com.novice.board.controller;

import com.novice.board.domain.User;
import com.novice.board.service.UserService;
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
    public String registerComplete(@ModelAttribute("username") String username, Model model){
        model.addAttribute("username", username);
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, RedirectAttributes redirectAttributes){
        userService.register(user);
        redirectAttributes.addAttribute("username", user.getUsername());
        return "redirect:/register-complete";
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("status") boolean status, Model model){
        model.addAttribute("status", status);
        return "user/login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        RedirectAttributes redirectAttributes){
        Optional<User> user = userService.login(username, password);
        boolean status = user.isPresent();
        redirectAttributes.addAttribute("status", status);
        if(status){
            return "redirect:/posts";
        } else return "redirect:/login";
    }

    @GetMapping("/posts")
    public String getPosts(){
        return "board/list";
    }
}
