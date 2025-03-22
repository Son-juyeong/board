package com.novice.board.service;

import com.novice.board.domain.Board;
import com.novice.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository=boardRepository;
    }

    public void postBoard(Board board, Long userid){
        board.setUserid(userid);
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String postdate = date.toString() + time.toString();
        board.setPostdate(postdate);
        boardRepository.save(board);
    }

    public void deleteBoard(Long boardid){
        boardRepository.delete(boardid);
    }

    public void updateBoard(Long boardid, Board boardParam){
        boardRepository.update(boardid, boardParam);
    }

    public Optional<Board> findBoard(Long boardid){
        return boardRepository.findById(boardid);
    }

    public List<Board> findBoardLists(){
        return boardRepository.findAll();
    }

}
