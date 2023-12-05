package com.example.ItProject.dto;

public class CellDTO {

    private int row;
    private int column;
    private String value;

    public CellDTO() {
    }

    public CellDTO(int row, int column, String value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }


    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
