package com.example.ItProject.controllers;

import com.example.ItProject.dto.BoardDTO;
import com.example.ItProject.models.Cell;
import com.example.ItProject.models.Game;
import com.example.ItProject.models.Player;
import com.example.ItProject.services.GameService;
import com.example.ItProject.services.PlayerService;
import jakarta.persistence.PrePersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    private final PlayerService playerService;
    @Autowired
    public GameController(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;

    }

    @GetMapping("/{gameId}")
    public ModelAndView gamePage(@PathVariable Long gameId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("game");
        return modelAndView;
    }


    @PostMapping("/create")
    public BoardDTO createNewGame() {
        return gameService.createNewGame();
    }

    @PostMapping("/{gameId}/makeMove")
    public BoardDTO makeMove(@PathVariable Long gameId, @RequestParam int row, @RequestParam int column, @RequestParam String turn) {
        return gameService.makeMove(gameId, row, column, turn);

    }

//    @GetMapping("/{gameId}")
//    public Game getGameById(@PathVariable Long gameId) {
//        return gameService.getGameById(gameId);
//    }


    @GetMapping("{gameId}/state")
    public ResponseEntity <List<Cell>>  getGameState(@PathVariable Long gameId){
        List<Cell> gameState = gameService.getGameStateByGameId(gameId);
        return ResponseEntity.ok().body(gameState);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteGameById(@PathVariable Long id) {
        gameService.deleteGameById(id);
    }

    @DeleteMapping("/delete")
    public void deleteAll() {
        gameService.deleteAll();
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getByNames(){

        return ResponseEntity.ok(playerService.getByName());
    }
    @PostMapping("/{gameId}/increaseBoardSize")
    public void increaseBoardSize(@PathVariable Long gameId) {
        gameService.increaseBoardSize(gameId);
    }

    @GetMapping("/{gameId}/checkWin/{turn}")
    public ResponseEntity<Boolean> checkWinCondition(@PathVariable Long gameId,
                                                     @PathVariable String turn) {
        List<Cell> cells = gameService.getGameStateByGameId(gameId);
        List<List<String>> board = gameService.convertCellsToBoard(cells, gameService.getGameById(gameId).getBoardSize());
        boolean isWin = gameService.checkWinCondition(board, turn);

        return ResponseEntity.ok().body(isWin);
    }

}
