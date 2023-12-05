document.addEventListener('DOMContentLoaded', () => {
    const gameContainer = document.querySelector('.game-container');
    let gameId;


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
                console.log(data);
                gameId = data;


                renderBoard([]);
            })
            .catch(error => {
                console.error('Error creating game:', error);
            });
    }

    createNewGame();


    function renderBoard(boardData) {
        gameContainer.innerHTML = '';



        boardData.forEach((row, rowIndex) => {
            const rowElement = document.createElement('div');
            rowElement.classList.add('row');

            row.forEach((cell, columnIndex) => {
                const cellElement = document.createElement('div');
                cellElement.classList.add('cell');
                cellElement.dataset.row = rowIndex;
                cellElement.dataset.column = columnIndex;
                cellElement.textContent = cell;

                rowElement.appendChild(cellElement);
            });

            gameContainer.appendChild(rowElement);
        });

    }

    // Event delegation for handling cell clicks
    gameContainer.addEventListener('click', (event) => {
        const clickedCell = event.target;
        if (clickedCell.classList.contains('cell')) {
            const row = clickedCell.dataset.row;
            const column = clickedCell.dataset.column;

            fetch(`/games/${gameId}/makeMove?row=${row}&column=${column}`, {
                method: 'POST'
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(updatedData => {

                    renderBoard(updatedData);
                })
                .catch(error => {
                    console.error('Error making move:', error);
                });
        }
    });
});
