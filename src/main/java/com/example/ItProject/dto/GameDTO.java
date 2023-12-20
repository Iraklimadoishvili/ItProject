package com.example.ItProject.dto;

import jakarta.persistence.ElementCollection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class GameDTO {
    @Setter
    private Long id;

    @Setter
    private List<PlayerDTO> players;

    @Setter
    private List<CellDTO> cells;

    @Setter
    private boolean isGameOver;

    @Setter
    private int movesLeft;

    @Setter
    private int currentPlayerIndex;

    @Setter
    private String winner;


    public GameDTO(){

    }
    public GameDTO(Long id, List<PlayerDTO> players, List<CellDTO> cells, boolean isGameOver, int movesLeft, int currentPlayerIndex, String winner) {
        this.id = id;
        this.players = players;
        this.cells = cells;
        this.isGameOver = isGameOver;
        this.movesLeft = movesLeft;
        this.currentPlayerIndex = currentPlayerIndex;
        this.winner = winner;
    }



    public GameDTO(Long id,List<CellDTO> cells) {
        this.id = id;
        this.cells= cells;
    }
}
