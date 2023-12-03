package com.example.ItProject.controllers;

import com.example.ItProject.models.Game;
import com.example.ItProject.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService){
        this.gameService = gameService;

    }

    @GetMapping("/game")
    public String gamePage() {
        return "game";
    }


    @PostMapping("/create")
    public Long createNewGame(){
        return gameService.createNewGame();
    }

    @PostMapping("/{gameId}/makeMove")
    public void makeMove(@PathVariable Long gameId,@RequestParam int row,@RequestParam int column){
        gameService.makeMove(gameId,row,column);
    }

    @GetMapping("/{gameId}")
    public Game getGameById(@PathVariable Long gameId){
        return gameService.getGameById(gameId);
    }
}
