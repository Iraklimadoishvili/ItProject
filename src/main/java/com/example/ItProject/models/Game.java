package com.example.ItProject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter
    private List<Player> players;

  @Getter
  @Setter
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cell> cells;

    @Column(name = "board_size")
    @Getter
    private int boardSize;

    @Getter
    private boolean isGameOver;

    @Getter
    private int movesLeft;

    @Getter
    private int currentPlayerIndex;

    @Getter
    private String winner;

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }


    public void setBoardSize(int size) {
        this.boardSize = size;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setIsGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public void setMovesLeft(int movesLeft) {
        this.movesLeft = movesLeft;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }


}
