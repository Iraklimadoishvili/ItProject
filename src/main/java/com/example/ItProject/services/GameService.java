package com.example.ItProject.services;

import com.example.ItProject.dto.NewGameDTO;
import com.example.ItProject.models.Cell;
import com.example.ItProject.models.Game;
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
    private final PlayerRepository playerRepository;

    private final CellRepository cellRepository;
    @Autowired
    public GameService(GameRepository gameRepository,PlayerRepository playerRepository,CellRepository cellRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.cellRepository = cellRepository;
    }

    public NewGameDTO createNewGame() {
        return createNewGameWithSize(20);
    }

    public NewGameDTO createNewGameWithSize(int size) {
        Game newGame = new Game();
        newGame.setBoardSize(size);
        initializeBoard(newGame, size);
       Game savedGame = gameRepository.save(newGame);
        return new NewGameDTO(savedGame.getId(),size);
    }

    public void makeMove(Long gameId, int row, int column) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found"));

        List<Cell> cells = game.getCells();
        Optional<Cell> targetCell = cells.stream()
                .filter(cell -> cell.getRowIndex() == row && cell.getColumnIndex() == column)
                .findFirst();

        if (targetCell.isPresent()) {
            Cell cell = targetCell.get();
            if (cell.getValue().isEmpty()) {
                cell.setValue("X");


                cellRepository.save(cell);
                gameRepository.save(game);

                int movesLeft = game.getMovesLeft();

                if (checkWinCondition(convertCellsToBoard(cells, game.getBoardSize()), row, column, "X")) {
                    game.setIsGameOver(true);
                    game.setWinner("Player X wins!");
                } else if (movesLeft == 0) {
                    game.setIsGameOver(true);
                    game.setWinner("It's a tie!");
                }


                if (movesLeft % 20 == 0 && movesLeft > 0) {
                    increaseBoardSize(gameId);
                }
            } else {
                throw new IllegalArgumentException("Invalid move: cell already occupied!");
            }
        } else {
            throw new IllegalArgumentException("Invalid move: cell not found!");
        }
    }

    private List<List<String>> convertCellsToBoard(List<Cell> cells, int boardSize) {
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

    private void increaseBoardSize(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Game not found"));

        int newSize = game.getBoardSize() + 40;
        initializeBoard(game, newSize);
        game.setBoardSize(newSize);
        gameRepository.save(game);
    }

    private boolean checkLine(List<List<String>> board, int row, int column, String symbol) {

        int count = 0;
        for (int c = 0; c < board.size(); c++) {
            if (board.get(row).get(c).equals(symbol)) {
                count++;
                if (count == 5) {
                    return true;
                }
            } else {
                count = 0;
            }
        }


        count = 0;
        for (int r = 0; r < board.size(); r++) {
            if (board.get(r).get(column).equals(symbol)) {
                count++;
                if (count == 5) {
                    return true;
                }
            } else {
                count = 0;
            }
        }



        return false;
    }
    private boolean checkDiagonals(List<List<String>> board, int row, int column, String symbol) {
        int size = board.size();

        int count = 0;
        for (int i = 0; i < size; i++) {
            if (row + i < size && column + i < size && board.get(row + i).get(column + i).equals(symbol)) {
                count++;
                if (count == 5) {
                    return true;
                }
            } else {
                break;
            }
        }


        count = 0;
        for (int i = 0; i < size; i++) {
            if (row + i < size && column - i >= 0 && board.get(row + i).get(column - i).equals(symbol)) {
                count++;
                if (count == 5) {
                    return true;
                }
            } else {
                break;
            }
        }

        return false;
    }

    private boolean checkWinCondition(List<List<String>> board, int row, int column, String symbol) {
        return checkLine(board, row, column, symbol) || checkDiagonals(board, row, column, symbol);
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
