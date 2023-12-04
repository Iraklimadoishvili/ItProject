package com.example.ItProject.dto;

import com.example.ItProject.models.Game;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
public class PlayerDTO {

    @Setter
    private Long id;

    @Setter
    @NotEmpty(message = "შეიყვანეთ სახელი!")
    private String name;

    @Setter
  private ColorDTO color;

      @Setter
    private int timeSpent;


      public PlayerDTO (){

      }

    public PlayerDTO(Long id, String name, ColorDTO color, int timeSpent) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.timeSpent = timeSpent;

    }
}
