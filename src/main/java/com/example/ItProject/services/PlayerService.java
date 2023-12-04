package com.example.ItProject.services;

import com.example.ItProject.dto.GameDTO;
import com.example.ItProject.dto.PlayerDTO;
import com.example.ItProject.models.Game;
import com.example.ItProject.models.Player;
import com.example.ItProject.repositories.GameRepository;
import com.example.ItProject.repositories.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.action.internal.EntityActionVetoException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository ,GameRepository gameRepository) {

        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    public Player addPlayer(@NotNull PlayerDTO playerDTO) {
        Player player = new Player();
        player.setName(playerDTO.getName());
        player.setColor(playerDTO.getColor());
        player.setTimeSpent(playerDTO.getTimeSpent());
      return playerRepository.save(player);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    public Player updatePlayer(Long id, PlayerDTO updatedPlayer) {
        Optional<Player> optionalPlayer = playerRepository.findById(id);
        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();
            player.setName(updatedPlayer.getName());
            player.setColor(updatedPlayer.getColor());
            player.setTimeSpent(updatedPlayer.getTimeSpent());
            return playerRepository.save(player);
        }
        return null;
    }

    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }
    public void deletePlayer(){
        playerRepository.deleteAll();
    }
}
