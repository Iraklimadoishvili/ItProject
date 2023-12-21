
document.addEventListener('DOMContentLoaded', () => {
    function fetchLeaderboardData() {
        fetch('/players/leaderboard/json')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                console.log(response)
                return response.json();
            })
            .then(leaderboardData => {
                console.log(leaderboardData);
                const leaderboardBody = document.getElementById('leaderboardBody');

                leaderboardData.forEach(playerData => {
                    const playerName = playerData.name;
                    const timeSpent = playerData.timeSpent;


                    const row = document.createElement('tr');
                    const playerNameCell = document.createElement('td');
                    playerNameCell.textContent = playerName;
                    const timeSpentCell = document.createElement('td');
                    timeSpentCell.textContent = `${timeSpent} seconds`;

                    row.appendChild(playerNameCell);
                    row.appendChild(timeSpentCell);

                    leaderboardBody.appendChild(row);
                });
            })
            .catch(error => {
                console.error('Error fetching leaderboard data:', error);
            });
    }

    fetchLeaderboardData();
});
