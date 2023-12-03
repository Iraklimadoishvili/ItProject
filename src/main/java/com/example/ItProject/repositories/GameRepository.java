package com.example.ItProject.repositories;

import com.example.ItProject.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository  extends JpaRepository<Game,Long> {

}
