package com.example.ItProject.repositories;

import com.example.ItProject.dto.PlayerDTO;
import com.example.ItProject.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository  extends JpaRepository<Player,Long> {

    @Query("select p.name from Player p")
    List<String> findAllPlayerNames();

    @Query("SELECT new com.example.ItProject.dto.PlayerDTO(p.name, p.timeSpent) FROM Player p ORDER BY p.timeSpent DESC")
    List<PlayerDTO> findLeaderboardData();
}
