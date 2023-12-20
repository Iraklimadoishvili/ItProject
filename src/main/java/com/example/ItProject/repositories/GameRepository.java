package com.example.ItProject.repositories;

import com.example.ItProject.dto.GameDTO;
import com.example.ItProject.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository  extends JpaRepository<Game,Long> {

  List<Game> findGameById(Long gameId);
}