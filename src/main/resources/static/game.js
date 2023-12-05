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
                 gameId = data.gameId;

                renderBoard(data.size);
            })
            .catch(error => {
                console.error('Error creating game:', error);
            });
    }

    createNewGame();

    function refreshBoard(row,column,value,boardSize) {
        gameContainer.innerHTML = '';
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
                if(i == row && j == column){
                    cellElement.dataset.value = value;
                }
                rowElement.appendChild(cellElement);
                gameContainer.appendChild(rowElement);


            }

        }

    }



    function renderBoard(boardSize) {
        gameContainer.innerHTML = '';
        for (let i = 0; i < boardSize; i++) {
            let rowIndex = i;
            let value = "X";
            const rowElement = document.createElement('div');
            rowElement.classList.add('row');
            for (let j = 0; j < boardSize; j++) {
                let columnIndex = j;
                const cellElement = document.createElement('div');
                cellElement.classList.add('cell');
                cellElement.dataset.row = rowIndex;
                cellElement.dataset.column = columnIndex;
                cellElement.dataset.value = value;
                rowElement.appendChild(cellElement);
                gameContainer.appendChild(rowElement);
            }

        }

    }


    gameContainer.addEventListener('click', (event) => {
        const clickedCell = event.target;
        if (clickedCell.classList.contains('cell')) {
            const row = clickedCell.dataset.row;
            const column = clickedCell.dataset.column;
           console.log("row",row,"column",column,"gameid",gameId)
            fetch(`/games/${gameId}/makeMove?row=${row}&column=${column}`, {
                method: 'POST'
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    console.log(response);
                    return JSON.parse('{"row":2,"column": 5,"value":"X"}'
                )



                })
                .then(updatedData => {

                    refreshBoard(updatedData.row,updatedData.column,updatedData.value,20);
                })
                .catch(error => {
                    console.error('Error making move:', error);
                });
        }
    });
});
