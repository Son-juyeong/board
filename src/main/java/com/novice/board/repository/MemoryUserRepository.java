package com.novice.board.repository;

import com.novice.board.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryUserRepository implements UserRepository{

    private static Map<Long, User> store = new HashMap<>();
    private static Long sequenceId = 0L;

    @Override
    public void save(User user) {
        user.setUserid(++sequenceId);
        store.put(user.getUserid(), user);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return Optional.ofNullable(store.get(userId));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return store.values().stream().filter(user -> user.getUsername().equals(username)).findAny();
    }
}
