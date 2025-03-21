package com.novice.board.repository;

import com.novice.board.domain.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    void save(Board board);
    void delete(Long boardid);
    void update(Long boardid, Board boardParam);
    Optional<Board> findById(Long boardid);
    List<Board> findAll();
}