document.addEventListener('DOMContentLoaded', () => {
    const battleground = document.querySelector('.battleground');

    // Fetch board data from the server
    fetch('/games/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ gridSize: 20 })
    })
        .then(response => response.json())
        .then(data => {
            renderBoard(data.board);
        })
        .catch(error => {
            console.error('Error creating game:', error);
        });

    function renderBoard(boardData) {
        if (Array.isArray(boardData) && boardData.length > 0) {
            battleground.innerHTML = '';

            boardData.forEach(row => {
                const rowElement = document.createElement('div');
                rowElement.classList.add('row');

                row.forEach(cell => {
                    const cellElement = document.createElement('div');
                    cellElement.classList.add('cell');

                    // Add content or styling to the cell based on cell data
                    cellElement.textContent = cell; // Example: Display the cell content as text

                    rowElement.appendChild(cellElement);
                });

                battleground.appendChild(rowElement);
            });
        } else {
            console.error('Board data is undefined or empty!');
        }
    }
});
