package com.example.ItProject.repositories;

import com.example.ItProject.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository  extends JpaRepository<Player,Long> {

}
