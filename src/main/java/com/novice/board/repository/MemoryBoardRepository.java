package com.novice.board.repository;

import com.novice.board.domain.Board;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryBoardRepository implements BoardRepository{

    private static Map<Long, Board> store = new HashMap<>();
    private static Long sequencdId = 0L;

    @Override
    public void save(Board board) {
        board.setBoardid(++sequencdId);
        store.put(board.getBoardid(), board);
    }

    @Override
    public void delete(Long boardid) {
        store.remove(boardid);
    }

    @Override
    public void update(Long boardid, Board boardParam) {
        Board board = findById(boardid).get();
        board.setBoardname(boardParam.getBoardname());
        board.setBoardtext(boardParam.getBoardtext());
    }

    @Override
    public Optional<Board> findById(Long boardid) {
        return Optional.ofNullable(store.get(boardid));
    }

    @Override
    public List<Board> findAll() {
        return store.values().stream().toList();
    }
}
