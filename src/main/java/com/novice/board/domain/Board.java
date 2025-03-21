package com.novice.board.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Board {
    private Long boardid;
    private String boardname;
    private String boardtext;
    private Long userid;
    private String postdate;

    public Board(String boardname, String boardtext, Long userid) {
        this.boardname = boardname;
        this.boardtext = boardtext;
        this.userid = userid;
    }
}
