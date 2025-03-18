package com.novice.board.service;

import com.novice.board.domain.User;
import com.novice.board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(User user){
        userRepository.save(user);
    }

    public Optional<User> login(String username, String password){
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()&&user.get().getPassword().equals(password))
            return user;
        else return Optional.empty();
    }
}
