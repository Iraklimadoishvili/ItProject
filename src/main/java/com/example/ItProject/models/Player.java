package com.example.ItProject.models;

import com.example.ItProject.dto.ColorDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Random;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @Column(name = "names")
    private String name;


    @Getter
    @Setter
    @Column(name = "time_spent")
    private Integer timeSpent;

   @Column(name = "color")
    private String color;



@Getter
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "game_id")
    private Game game;

    public String getColor() {
        if (this.color == null || this.color.isEmpty()) {
            ColorDTO randomColor = getRandomColor();
            this.color = convertColor(randomColor);
        }
        return this.color;
    }

    public void setColor(ColorDTO colorDTO) {
        this.color = convertColor(colorDTO);
    }

    private ColorDTO getRandomColor() {
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        return new ColorDTO(red, green, blue);
    }

    private String convertColor(ColorDTO colorDTO) {
        return colorDTO.getRed() + "," + colorDTO.getGreen() + "," + colorDTO.getBlue();
    }


    public void setGame(Game game) {
        this.game = game;
    }
}


