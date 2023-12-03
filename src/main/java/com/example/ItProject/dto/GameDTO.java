package com.example.ItProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class GameDTO {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private List<PlayerDTO> players;

    @Getter
    @Setter
    private List<String> board;

    @Getter
    @Setter
    private boolean isGameOver;

    @Getter
    @Setter
    private int movesLeft;

    @Getter
    @Setter
    private int currentPlayerIndex;

    @Getter
    @Setter
    private String winner;


    public GameDTO(Long id, List<PlayerDTO> players, List<String> board, boolean isGameOver, int movesLeft, int currentPlayerIndex, String winner) {
        this.id = id;
        this.players = players;
        this.board = board;
        this.isGameOver = isGameOver;
        this.movesLeft = movesLeft;
        this.currentPlayerIndex = currentPlayerIndex;
        this.winner = winner;
    }
}
