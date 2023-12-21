document.addEventListener('DOMContentLoaded', () => {
    const player1NameElement = document.getElementById('player1Name');
    const player2NameElement = document.getElementById('player2Name');
    const savedGameState = sessionStorage.getItem('ticTacToeGameState');
    const player1Name = sessionStorage.getItem('Player1');
    const player2Name = sessionStorage.getItem('Player2');
    const player1TimerElement = document.getElementById('player1Timer');
    const player2TimerElement = document.getElementById('player2Timer');
    const gameTimerElement = document.getElementById('gameTimer');
    let player1Timer, player2Timer, gameTimerInterval;
    let currentPlayer = 'player1';
    let player1StartTime = null;
    let player2StartTime = null;
    player1NameElement.textContent = player1Name;
    player2NameElement.textContent = player2Name;
    const gameContainer = document.querySelector('.game-container');
    let gameId;
    let turn = "X";
    let game = {
        "gameId": 1,
        "cells": [],
        "size": 20
    };

    if (savedGameState) {
        game = JSON.parse(savedGameState);
        gameId = game.gameId;
        const remainingGameTime = sessionStorage.getItem('remainingGameTime');
        if (remainingGameTime) {
            startGameTimer(parseInt(remainingGameTime, 10));
        } else {
            startGameTimer(15 * 60);
        }
        renderBoard(game.size, game.cells, gameId);
    } else {
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
                return response.json();
            })
            .then(data => {
                gameId = data.gameId;
                renderBoard(data.size, data.cells);
            })
            .catch(error => {
                console.error('Error creating game:', error);
            });
    }

    function startGameTimer(durationInSeconds = 15 * 60) {
        let remainingTime = durationInSeconds;

        gameTimerInterval = setInterval(() => {
            const minutes = Math.floor(remainingTime / 60);
            const seconds = remainingTime % 60;

            gameTimerElement.textContent = `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
            sessionStorage.setItem('remainingGameTime', remainingTime);

            if (remainingTime <= 0) {
                clearInterval(gameTimerInterval);
                sessionStorage.removeItem('remainingGameTime');
            }
            remainingTime--;
        }, 1000);
    }

    function startPlayerTimer(player) {
        let moveDurationInSeconds = 30 * 1000;
        const startTime = Date.now();

        return setInterval(() => {
            const currentTime = Date.now();
            const elapsedTime = currentTime - startTime;
            const remainingTime = moveDurationInSeconds - elapsedTime;

            const minutes = Math.floor((remainingTime / 1000) / 60);
            const seconds = Math.floor((remainingTime / 1000) % 60);

            if (player === 'player1') {
                player1TimerElement.textContent = `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
            } else {
                player2TimerElement.textContent = `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
            }

            if (remainingTime <= 0) {
                clearInterval(player === 'player1' ? player1Timer : player2Timer);
                currentPlayer = currentPlayer === 'player1' ? 'player2' : 'player1';

                const endTime = Date.now();
                const timeSpent = endTime - startTime;
                savePlayerName(player === 'player1' ? player1Name : player2Name, timeSpent);


            }

        }, 1000);
    }

    function renderBoard(boardSize, cells, gameId) {
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
                cellElement.dataset.value = cells[i * 20 + j].value;
                cellElement.textContent = cells[i * 20 + j].value;
                rowElement.appendChild(cellElement);
                gameContainer.appendChild(rowElement);
            }
        }
        const gameState = {
            gameId: gameId,
            size: boardSize,
            cells: cells,
        };
        sessionStorage.setItem('ticTacToeGameState', JSON.stringify(gameState));
    }

    gameContainer.addEventListener('click', (event) => {
        clearInterval(player1Timer);
        clearInterval(player2Timer);

        const clickedCell = event.target;
        if (clickedCell.classList.contains('cell')) {
            const row = clickedCell.dataset.row;
            const column = clickedCell.dataset.column;


            if (currentPlayer === 'player1') {
                player1StartTime = new Date();
            } else {
                player2StartTime = new Date();
            }

            fetch(`/games/${gameId}/makeMove?row=${row}&column=${column}&turn=${currentPlayer === 'player1' ? 'X' : 'O'}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(updatedData => {
                    renderBoard(updatedData.size, updatedData.cells, updatedData.gameId);
                    currentPlayer = currentPlayer === 'player1' ? 'player2' : 'player1';

                    if (currentPlayer === 'player1') {
                        player1Timer = startPlayerTimer('player1');
                    } else {
                        player2Timer = startPlayerTimer('player2');
                    }

                    checkWin(gameId, row, column, currentPlayer === 'player1' ? 'X' : 'O');
                })
                .catch(error => {
                    console.error('Error making move:', error);
                });
        }
    });

    function checkWin(gameId, row, column, turn) {
        fetch(`/games/${gameId}/checkWin/${turn}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('network response was not ok');
                }
                console.log("response1",response);
                return response.json();
            })
            .then(isWin => {
                if (isWin) {
                    alert(`Player ${turn} wins`);
                    window.location.href = 'http://localhost:8080/';
                    sessionStorage.clear();
                } else {
                    console.log('no winner yet');
                }
            })
            .catch(error => {
                console.error('error checking win condition', error);
            });
    }


    const leaderboardButton = document.getElementById('leaderboardButton');
    leaderboardButton.addEventListener('click', () => {
        window.location.href = 'http://localhost:8080/players/leaderboard';
    });
});



