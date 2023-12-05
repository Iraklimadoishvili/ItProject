package com.example.ItProject.models;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "cell")
public class Cell {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private Game game;

    @Column(name = "row_index")
    @Getter
    private int rowIndex;
    @Column(name = "column_index")
    @Getter
    private int columnIndex;

    @Column(name = "cell_value")
    @Getter
    private String value;

    public Cell() {
    }

    public Cell(Long id, Game game, int rowIndex, int columnIndex, String value) {
        this.id = id;
        this.game = game;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.value = value;
    }

    public Cell(int i, int j, String s) {
        this.rowIndex = i;
        this.columnIndex = j;
        this.value = s;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public void setValue(String value) {
        this.value = value;
    }
}