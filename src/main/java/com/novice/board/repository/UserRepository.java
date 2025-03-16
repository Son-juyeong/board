package com.novice.board.repository;

import com.novice.board.domain.User;

import java.util.Optional;

public interface UserRepository {
    void save(User user);

    Optional<User> findById(Long userId);

    Optional<User> findByUsername(String username);
}
