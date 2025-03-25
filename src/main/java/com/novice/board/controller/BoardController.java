package com.novice.board.controller;

import com.novice.board.domain.Board;
import com.novice.board.domain.User;
import com.novice.board.service.BoardService;
import com.novice.board.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/posts")
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;

    @Autowired
    public BoardController(BoardService boardService, UserService userService) {
        this.boardService = boardService;
        this.userService = userService;
    }

    @GetMapping()
    public String getPostsList(HttpSession session, Model model){
        List<Board> boardLists = boardService.findBoardLists();
        model.addAttribute("isLogin", session.getAttribute("userId")!=null);
        model.addAttribute("boards", boardLists);
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
        //optionalBoard.ifPresent(board -> model.addAttribute("board", board));
        if(optionalBoard.isPresent()){
            Board board = optionalBoard.get();
            model.addAttribute("board", board);
            User user = userService.findUser(board.getUserid()).get();
            model.addAttribute("user", user);
        }
        return "board";
    }

    @GetMapping("/{boardid}/edit")
    public String editBoardForm(@PathVariable Long boardid, Model model){
        Optional<Board> optionalBoard = boardService.findBoard(boardid);
        if(optionalBoard.isPresent()){
            Board board = optionalBoard.get();
            model.addAttribute("board", board);
            User user = userService.findUser(board.getUserid()).get();
            model.addAttribute("user", user);
        }
        return "board/editBoardForm";
    }

    @PostMapping("/{boardid}/edit")
    public String editBoard(@PathVariable Long boardid, @ModelAttribute Board board){
        boardService.updateBoard(boardid, board);
        return "redirect:/posts/"+boardid;
    }
}
