document.addEventListener('DOMContentLoaded', () => {
    const player1NameElement = document.getElementById('player1Name');
    const player2NameElement = document.getElementById('player2Name');
    const savedGameState = sessionStorage.getItem('ticTacToeGameState');
    const player1Name = sessionStorage.getItem('Player1');
    const player2Name = sessionStorage.getItem('Player2');
    const player1TimerElement = document.getElementById('player1Timer');
    const player2TimerElement = document.getElementById('player2Timer');
     const gameTimerElement = document.getElementById('gameTimer');

    player1NameElement.textContent = player1Name;
    player2NameElement.textContent = player2Name;
    const gameContainer = document.querySelector('.game-container');
    let gameId;
    let turn = "X";
  let game = {
    "gameId": 1,
     "cells":[],
      "size":20
  };

  if(savedGameState){
      game = JSON.parse(savedGameState);
      playerTimer = gameTimerElement;
      gameId = game.gameId;
      renderBoard(game.size,game.cells,gameId,gameTimerElement);
  }
  else{
      createNewGame();
      startGameTimer();
  }
    function createNewGame() {
        fetch('/games/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                console.log(response)
                return response.json();
                console.log("response", response);
            })
            .then(data => {
                console.log("game id : ", gameId,gameTimerElement);
                 gameId = data.gameId;

                renderBoard(data.size,data.cells);
                console.log(renderBoard(data.size,data.cells,data.gameId))
            })
            .catch(error => {
                console.error('Error creating game:', error);
            });
    }

//    createNewGame();

    function startGameTimer(){
      const gameDurationInSeconds = 15*60;
      let remainingTime = gameDurationInSeconds;

      const gameInterval = setInterval(() =>{
          const minutes = Math.floor(remainingTime /60);
          const seconds = remainingTime %60;

          gameTimerElement.textContent =`${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;

          if(remainingTime <= 0){
              clearInterval(gameInterval);

          }
          remainingTime--;

      },1000);
    }

    function startPlayerTimer(player) {
        let moveDurationInSeconds = 30; // 30 seconds per move

        return setInterval(() => {
            const minutes = Math.floor(moveDurationInSeconds / 60);
            const seconds = moveDurationInSeconds % 60;

            // Display remaining time for the player's move
            if (player === 'player1') {
                player1TimerElement.textContent = `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
            } else {
                player2TimerElement.textContent = `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
            }

            if (moveDurationInSeconds <= 0) {
                clearInterval(player === 'player1' ? player1Timer : player2Timer);
                // Handle move time end for the player
                return;
            }

            moveDurationInSeconds--;
        }, 1000); // Update timer every second
    }




    function renderBoard(boardSize,cells,gameId) {
        gameContainer.innerHTML = "";
        for (let i = 0; i < boardSize; i++) {
            let rowIndex = i;
            const rowElement = document.createElement('div');
            rowElement.classList.add('row');
            for (let j = 0; j < boardSize; j++) {
                let columnIndex = j;
                const cellElement = document.createElement('div');
                cellElement.classList.add('cell');
                cellElement.dataset.row = rowIndex;
                cellElement.dataset.column = columnIndex;
                cellElement.dataset.value = cells[i*20 + j].value;
                cellElement.textContent = cells[i*20 + j].value;
                rowElement.appendChild(cellElement);
                gameContainer.appendChild(rowElement);
            }

        }
        const gameState = {
            gameId: gameId,
            size:boardSize,
            cells:cells,
        };
        sessionStorage.setItem('ticTacToeGameState',JSON.stringify(gameState));


    }


    gameContainer.addEventListener('click', (event) => {
       let player1Timer = startPlayerTimer('player1');

       clearInterval(player1Timer);

       let player2Timer = startPlayerTimer('player2');

       clearInterval(player2Timer);
        const clickedCell = event.target;
        if (clickedCell.classList.contains('cell')) {
            const row = clickedCell.dataset.row;
            const column = clickedCell.dataset.column;
           console.log("row",row,"column",column,"gameid",gameId)
            fetch(`/games/${gameId}/makeMove?row=${row}&column=${column}&turn=${turn}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    console.log(response);
                    return response.json();
                })
                .then(updatedData => {
                 console.log(updatedData);
                    renderBoard(updatedData.size,updatedData.cells,updatedData.gameId);
                    if(turn === "X"){
                        turn = "O";
                    }else {
                        turn = "X";
                    }
                    checkWin(gameId,row,column,turn)
                })
                .catch(error => {
                    console.error('Error making move:', error);
                });
        }
    });

  function checkWin(gameId,row,column,turn){
      fetch(`/games/${gameId}/checkWin/${turn}`,{
          method:'GET',
          headers:{
              'Content-Type':'application/json'
          }

      })
          .then(response =>{
              if(!response.ok){
                  throw new Error('network response was not ok');

              }
              return response.json();

          })
          .then(isWin =>{
              console.log('isWin' ,isWin);
              if(isWin){

                  alert(`Player ${turn} wins`);
                  window.location.href = 'http://localhost:8080/'
                  sessionStorage.clear();
              }else{
                  console.log('no winner yet');
              }
          })
          .catch(error =>{
              console.error('error checking win condition',error);
          });
  }
});

