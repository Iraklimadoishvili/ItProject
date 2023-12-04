package com.example.ItProject.dto;

import lombok.Getter;

@Getter
public class ColorDTO {
    private int red;
    private int green;
    private int blue;


    public ColorDTO(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }


    public ColorDTO(String colorString) {
        String[] colors = colorString.split(",");
        if (colors.length == 3) {
            this.red = Integer.parseInt(colors[0]);
            this.green = Integer.parseInt(colors[1]);
            this.blue = Integer.parseInt(colors[2]);
        } else {
            this.red = 0;
            this.green = 0;
            this.blue = 0;
        }
    }
}
