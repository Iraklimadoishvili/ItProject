package com.example.ItProject.services;

import com.example.ItProject.dto.BoardDTO;
import com.example.ItProject.dto.GameDTO;
import com.example.ItProject.models.Cell;
import com.example.ItProject.models.Game;
import com.example.ItProject.models.Player;
import com.example.ItProject.repositories.CellRepository;
import com.example.ItProject.repositories.GameRepository;
import com.example.ItProject.repositories.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final CellRepository cellRepository;
    @Autowired
    public GameService(GameRepository gameRepository,CellRepository cellRepository) {
        this.gameRepository = gameRepository;
        this.cellRepository = cellRepository;
    }

    public List<Cell> getGameStateByGameId (Long gameId){
        return cellRepository.findByGameId(gameId);
    }

    public BoardDTO createNewGame() {
        return createNewGameWithSize(20);
    }

    public BoardDTO createNewGameWithSize(int size) {
        Game newGame = new Game();
        newGame.setBoardSize(size);
        initializeBoard(newGame, size);
       Game savedGame = gameRepository.save(newGame);
        return new BoardDTO(savedGame.getId(),size,savedGame.getCells());
    }

    public BoardDTO makeMove(Long gameId, int row, int column, String turn) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found"));

        List<Cell> cells = game.getCells();
        Optional<Cell> targetCell = cells.stream()
                .filter(cell -> cell.getRowIndex() == row && cell.getColumnIndex() == column)
                .findFirst();

        if (targetCell.isPresent()) {
            Cell cell = targetCell.get();
            if (cell.getValue().isEmpty()) {
                cell.setValue(turn);
                cellRepository.save(cell);
                gameRepository.save(game);

                if (checkWinCondition(convertCellsToBoard(cells, game.getBoardSize()), turn)) {
                    game.setIsGameOver(true);
                    game.setWinner("Player " + turn + " wins!");
                } else {
                    int movesLeft = game.getMovesLeft();
                    if (movesLeft == 0) {
                        game.setIsGameOver(true);
                        game.setWinner("It's a tie!");
                    } else if (movesLeft % 20 == 0) {
                        increaseBoardSize(gameId);
                    }
                }
            } else {
                throw new IllegalArgumentException("Invalid move: cell already occupied!");
            }
        } else {
            throw new IllegalArgumentException("Invalid move: cell not found!");
        }
        return new BoardDTO(game.getId(), game.getBoardSize(), game.getCells());
    }


    public List<List<String>> convertCellsToBoard(List<Cell> cells, int boardSize) {
        List<List<String>> board = new ArrayList<>();
        for (int i = 0; i < boardSize; i++) {
            List<String> row = new ArrayList<>();
            for (int j = 0; j < boardSize; j++) {
                row.add("");
            }
            board.add(row);
        }

        for (Cell cell : cells) {
            int rowIndex = cell.getRowIndex();
            int columnIndex = cell.getColumnIndex();
            board.get(rowIndex).set(columnIndex, cell.getValue());
        }

        return board;
    }

    private void initializeBoard(Game game, int size) {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Cell cell = new Cell(i, j, "");
                cell.setGame(game);
                cells.add(cell);
            }
        }
        game.setCells(cells);
    }

    public void increaseBoardSize(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found"));

        int newSize = game.getBoardSize() + 40;
        initializeBoard(game, newSize);
        game.setBoardSize(newSize);
        gameRepository.save(game);
    }

    public boolean checkWinCondition(List<List<String>> board, String turn) {
        int size = board.size();
        int consecutiveCount = 0;

        // Check rows and columns
        for (int i = 0; i < size; i++) {
            consecutiveCount = 0;
            for (int j = 0; j < size; j++) {
                if (board.get(i).get(j).equals(turn)) {
                    consecutiveCount++;
                    if (consecutiveCount == 5) {
                        return true;
                    }
                } else {
                    consecutiveCount = 0;
                }
            }

            consecutiveCount = 0;
            for (int j = 0; j < size; j++) {
                if (board.get(j).get(i).equals(turn)) {
                    consecutiveCount++;
                    if (consecutiveCount == 5) {
                        return true;
                    }
                } else {
                    consecutiveCount = 0;
                }
            }
        }


        for (int i = 0; i <= size - 5; i++) {
            for (int j = 0; j <= size - 5; j++) {

                consecutiveCount = 0;
                for (int k = 0; k < 5; k++) {
                    if (board.get(i + k).get(j + k).equals(turn)) {
                        consecutiveCount++;
                        if (consecutiveCount == 5) {
                            return true;

                        }
                    } else {
                        consecutiveCount = 0;
                    }
                }


                consecutiveCount = 0;
                for (int k = 0; k < 5; k++) {
                    if (board.get(i + k).get(size - 1 - j - k).equals(turn)) {
                        consecutiveCount++;
                        if (consecutiveCount == 5) {
                            return true;
                        }
                    } else {
                        consecutiveCount = 0;
                    }
                }
            }
        }

        return false;
    }


    public Game getGameById(Long gameId){
        return gameRepository.findById(gameId).orElseThrow(() -> new EntityNotFoundException("Game not found"));
    }



    public void deleteGameById(Long id){
        gameRepository.deleteById(id);
    }

    public void deleteAll(){
        gameRepository.deleteAll();
    }


}
