package com.example.ItProject.services;

import com.example.ItProject.models.Game;
import com.example.ItProject.repositories.GameRepository;
import com.example.ItProject.repositories.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.cert.Extension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GameService {

    private final GameRepository gameRepository;
  private final PlayerRepository playerRepository;
    @Autowired
    public GameService(GameRepository gameRepository,PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

  public Long createNewGame(){
    return createNewGameWithSize(20);
  }
public Long createNewGameWithSize(int size){
        Game newGame = new Game();
        newGame.setBoardSize(size);

        List<String> initialBoard = new ArrayList<>(Collections.nCopies(size *size, ""));
        newGame.setBoard(initialBoard);
        newGame.setIsGameOver(false);
        newGame.setMovesLeft(size*size);
     Game savedGame = gameRepository.save(newGame);
    return savedGame.getId();
    }


  public void makeMove(Long gameId, int row, int column){
      Game game = gameRepository.findById(gameId)
              .orElseThrow(() -> new EntityNotFoundException("Game not found"));

      List<String> board = game.getBoard();
      int index = row * 20 + column;
      if(board.get(index).isEmpty()){
          board.set(index,"X");
        game.setBoard(board);


          if(checkWinCondition(board)){
              game.setIsGameOver(true);
          }

          game.setMovesLeft(game.getMovesLeft() - 1);
          if(game.getMovesLeft() == game.getBoardSize() * game.getBoardSize() - 20 ){
              increaseBoardSize(gameId);
          }
          gameRepository.save(game);
      }else{
          throw new IllegalArgumentException("Invalid move: cell already occupied!");
      }

  }

    private boolean checkLine(List<String> line) {
        String firstSymbol = line.get(0);
        if (firstSymbol.equals("")) {
            return false;
        }

        for (String symbol : line) {
            if (!symbol.equals(firstSymbol)) {
                return false;
            }
        }

        return true;
    }

  private boolean checkWinCondition(List<String> board){
      for (int i = 0; i < board.size(); i+=20) {
          if(checkLine(board.subList(i , i + 20))){
              return true;
          }

      }
      for (int i = 0; i < 20; i++) {
          List<String> column = new ArrayList<>();
          for (int j = 0; j < board.size(); j+=20) {
              column.add(board.get(j));

          }
          if(checkLine(column)){
              return true;
          }
      }

      List<String> diagonal1 = new ArrayList<>();
      List<String> diagonal2 = new ArrayList<>();

      for (int i = 0; i < board.size(); i+=21) {
          diagonal1.add(board.get(i));

      }
      for (int i = 19; i < board.size()-19  ; i+=19) {
          diagonal2.add(board.get(i));

      }
      if(checkLine(diagonal1)|| checkLine(diagonal2)){
          return true;
      }
      return false;
  }

  public Game getGameById(Long gameId){
        return gameRepository.findById(gameId).orElseThrow(() -> new EntityNotFoundException("Game not found"));
  }

  private void increaseBoardSize(Long gameId){
        Game game = gameRepository.findById(gameId).
                orElseThrow(() -> new EntityNotFoundException("game not found"));

        int newSize  = game.getBoardSize() * 2;
        List<String> enlargedBoard = new ArrayList<>(Collections.nCopies(newSize *newSize, ""));
        game.setBoard(enlargedBoard);
        gameRepository.save(game);
  }


  public void deleteGameById(Long id){
         gameRepository.deleteById(id);
  }

  public void deleteAll(){
        gameRepository.deleteAll();
  }
}
