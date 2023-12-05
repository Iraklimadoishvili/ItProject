package com.example.ItProject.controllers;

import com.example.ItProject.dto.NewGameDTO;
import com.example.ItProject.models.Cell;
import com.example.ItProject.models.Game;
import com.example.ItProject.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService){
        this.gameService = gameService;

    }

    @GetMapping("/game")
    public ModelAndView gamePage() {
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.setViewName("game");

      return modelAndView;
    }


    @PostMapping("/create")
    public NewGameDTO createNewGame(){
        return gameService.createNewGame();
    }

    @PostMapping("/{gameId}/makeMove")
    public void makeMove(@PathVariable Long gameId, @RequestParam int row, @RequestParam int column){
        gameService.makeMove(gameId, row, column);
    }

    @GetMapping("/{gameId}")
    public Game getGameById(@PathVariable Long gameId){
        return gameService.getGameById(gameId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteGameById(@PathVariable Long id){
        gameService.deleteGameById(id);
    }

    @DeleteMapping("/delete")
    public void deleteAll(){
        gameService.deleteAll();
    }
}
