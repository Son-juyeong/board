package com.novice.board.controller;

import com.novice.board.domain.Board;
import com.novice.board.service.BoardService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/posts")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping()
    public String getPostsList(HttpSession session, Model model){
        model.addAttribute("isLogin", session.getAttribute("userId")!=null);
        return "board/list";
    }
    @GetMapping("/new")
    public String addPostsForm(){
        return "board/addBoardForm";
    }
    @PostMapping("/new")
    public String addPosts(@ModelAttribute Board board, HttpSession session){
        Long userId = Long.parseLong(session.getAttribute("userId").toString());
        boardService.postBoard(board, userId);
        return "redirect:/posts/"+board.getBoardid();
    }

    @GetMapping("/{boardid}")
    public String getPosts(@PathVariable Long boardid, Model model){
        Optional<Board> optionalBoard = boardService.findBoard(boardid);
        optionalBoard.ifPresent(board -> model.addAttribute("board", board));
        return "board";
    }
}
