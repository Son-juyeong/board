package com.novice.board.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class User {
    private Long userid;
    private String username;
    private String nickname;
    private String password;

    public User(String username, String nickname, String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }
}
