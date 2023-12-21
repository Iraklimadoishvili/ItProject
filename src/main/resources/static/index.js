const Player1 = document.getElementById('player1');
const Player2 = document.getElementById('player2');
const readyBtn1 = document.getElementById('readyPlayer1');
const readyBtn2 = document.getElementById('readyPlayer2');
const startBtn = document.getElementById('startGame');
let player1StartTime = null;
let player2StartTime = null;
Player1.addEventListener('input', handleInputChange);
Player2.addEventListener('input', handleInputChange);

 let gameId;

function handleInputChange() {
    const player1name = Player1.value.trim();
    const player2name = Player2.value.trim();

    readyBtn1.disabled = player1name.length === 0;
    readyBtn2.disabled = player2name.length === 0;

    startBtn.disabled = !(!readyBtn1.disabled && !readyBtn2.disabled);
}



function savePlayerName(playerName,timeSpent) {
    const color = generateRandomColor();
    const timeSpentInSeconds = Math.floor(timeSpent / 1000);


    const playerData = {
        name: playerName,
        timeSpent: timeSpentInSeconds,
        color: color
    };

    fetch(`/players/add`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(playerData)
    })
        .then(response => {
            if (response.ok) {

                console.log('Player name saved successfully!',response.timeSpent);
            } else {
                console.error('Failed to save player name!');
            }
        })
        .catch(error => {
            console.error('Error', error);
        });
}



function generateRandomColor() {
    const red = Math.floor(Math.random() * 256);
    const green = Math.floor(Math.random() * 256);
    const blue = Math.floor(Math.random() * 256);
    return `${red},${green},${blue}`;
}

startBtn.addEventListener('click', () => {
    fetch('/games/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({})
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Failed to create game!');
            }
        })
        .then(data => {
            console.log('Game ID:', data.gameId);
            gameId = data.gameId;

            sessionStorage.setItem('Player1', Player1.value.trim());
            sessionStorage.setItem('Player2', Player2.value.trim());

            window.location.href = `/games/${gameId}`;

            console.log(gameId);
        })
        .catch(error => {
            console.error('Error creating game:', error);
        });
});

readyBtn1.addEventListener('click', () => {
    const player1EndTime = new Date();
    const timeSpentPlayer1 = player1EndTime - player1StartTime;

    savePlayerName(Player1.value.trim(), timeSpentPlayer1);
});

readyBtn2.addEventListener('click', () => {
    const player2EndTime = new Date();
    const timeSpentPlayer2 = player2EndTime - player2StartTime;

    savePlayerName(Player2.value.trim(), timeSpentPlayer2);
});
