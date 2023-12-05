package com.example.ItProject.controllers;


import com.example.ItProject.dto.GameDTO;
import com.example.ItProject.dto.PlayerDTO;
import com.example.ItProject.models.Player;
import com.example.ItProject.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("players")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/add")
    public ResponseEntity<Player> addPlayer(@RequestBody PlayerDTO playerDTO) {
        Player addedPlayer = playerService.addPlayer(playerDTO);
        return new ResponseEntity<>(addedPlayer, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Player>> getAllPlayers() {
        List<Player> players = playerService.getAllPlayers();
        return ResponseEntity.ok(players);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        Optional<Player> player = playerService.getPlayerById(id);
        return player.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id, @RequestBody PlayerDTO updatedPlayer) {
        Player player = playerService.updatePlayer(id, updatedPlayer);
        return player != null ? ResponseEntity.ok(player) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> DeletePlayer(){
         playerService.deletePlayer();
         return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
}
