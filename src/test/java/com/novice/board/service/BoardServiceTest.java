package com.novice.board.service;

import com.novice.board.BoardApplication;
import com.novice.board.domain.Board;
import com.novice.board.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class BoardServiceTest {

    @Test
    void save(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(BoardApplication.class);
        UserService userService = ac.getBean("userService", UserService.class);
        BoardService boardService = ac.getBean("boardService", BoardService.class);
        User user = new User("spring", "스프링", "123456");
        userService.register(user);
        Board board = new Board("글", "글이다.", user.getUserid());
        boardService.postBoard(board, board.getUserid());
        Board findBoard = boardService.findBoard(board.getBoardid()).get();
        assertThat(findBoard).isEqualTo(board);
    }

    @Test
    void findAll(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(BoardApplication.class);
        UserService userService = ac.getBean("userService", UserService.class);
        BoardService boardService = ac.getBean("boardService", BoardService.class);
        User user1 = new User("spring1", "스프링1", "123456");
        userService.register(user1);
        Board board1 = new Board("글1", "글이다.1", user1.getUserid());
        User user2 = new User("spring2", "스프링2", "1234567");
        userService.register(user2);
        Board board2 = new Board("글2", "글이다.2", user2.getUserid());
        boardService.postBoard(board1, board1.getUserid());
        boardService.postBoard(board2, board2.getUserid());
        List<Board> boardLists = boardService.findBoardLists();
        assertThat(boardLists.size()).isEqualTo(2);
    }

    @Test
    void update(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(BoardApplication.class);
        UserService userService = ac.getBean("userService", UserService.class);
        BoardService boardService = ac.getBean("boardService", BoardService.class);
        User user = new User("hello", "인사", "123456");
        userService.register(user);
        Board board = new Board("제목", "본문", user.getUserid());
        boardService.postBoard(board, board.getUserid());
        Long id = board.getBoardid();
        Board updateBoard = new Board("제목", "업데이트", user.getUserid());
        boardService.updateBoard(id, updateBoard);
        Board findBoard = boardService.findBoard(board.getBoardid()).get();
        assertThat(findBoard.getBoardtext()).isEqualTo(updateBoard.getBoardtext());
    }

    @Test
    void delete(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(BoardApplication.class);
        UserService userService = ac.getBean("userService", UserService.class);
        BoardService boardService = ac.getBean("boardService", BoardService.class);
        User user = new User("user", "사용자", "12356");
        userService.register(user);
        Board board = new Board("제목입니다.", "작성합니다.", user.getUserid());
        boardService.postBoard(board, board.getUserid());
        Long id = board.getBoardid();
        boardService.deleteBoard(id);
        Optional<Board> findBoard = boardService.findBoard(id);
        assertThat(findBoard.isEmpty()).isTrue();
    }
}
