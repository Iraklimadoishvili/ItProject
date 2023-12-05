package com.example.ItProject.dto;

public class NewGameDTO {

    private long gameId;

    private int size;

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

    public NewGameDTO(long gameId, int size) {
        this.gameId = gameId;
        this.size = size;
    }
}
