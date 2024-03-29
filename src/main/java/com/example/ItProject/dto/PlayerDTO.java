package com.example.ItProject.dto;

import com.example.ItProject.models.Game;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



public class PlayerDTO {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    @NotEmpty(message = "შეიყვანეთ სახელი!")
    private String name;

    @Getter
    @Setter
  private ColorDTO color;

    @Getter
      @Setter
    private Long timeSpent;


      public PlayerDTO (){

      }

    public PlayerDTO(Long id, String name, ColorDTO color, Long timeSpent) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.timeSpent = timeSpent;

    }

    public PlayerDTO(String name,Long timeSpent){
          this.name =name;
          this.timeSpent = timeSpent;
    }



}
