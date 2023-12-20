package com.example.ItProject.dto;

import com.example.ItProject.models.Cell;
import jakarta.persistence.*;

import java.util.List;

public class BoardDTO {

    private long gameId;

    private int size;

    private List<Cell> cells;

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public BoardDTO(long gameId, int size, List<Cell> cells) {
        this.gameId = gameId;
        this.size = size;
        this.cells = cells;
    }

}
