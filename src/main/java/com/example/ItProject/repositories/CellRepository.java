package com.example.ItProject.repositories;

import com.example.ItProject.models.Cell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CellRepository extends JpaRepository<Cell, Long> {
    List<Cell> findByGameId(Long gameId);
}
