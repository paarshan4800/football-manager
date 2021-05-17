# Football Manager

A desktop application which can be used by football managers to manage their team, and the transfer market.

- View current league standings
- View top scorers of the league
- View recent results of all teams
- View upcoming fixtures of all teams
- View all players (filter by position, country, age and team)
- View player data
- Make transfer request (permanent, loan and player exchange)
- View Incoming/Outgoing Transfer Requests
- Accept/Reject Transfer Requests

## Setup

### Download and Installation

- [Install Java](https://www.youtube.com/watch?v=IJ-PJbvJBGs)
- [Download and Install MySQL](https://www.youtube.com/watch?v=WuBcTJnIuzo)
- Clone and open the project in any Java IDE ([IntelliJ](https://www.jetbrains.com/idea/download/))

### Setting up dependencies (External JAR)

- Add the external JAR files in the folder ```External JARs``` to your project.

### Setting up API Key

- Get API Key from
    - [API Football](https://apifootball.com/) (used to populate managers, teams, players, league standings and top
      scorers)
    - [Football Data](https://www.football-data.org/) (used to populate fixtures and results)


- In the file ```src/com/api/API.java```, enter your two API keys from [API Football](https://apifootball.com/)
  and [Football Data](https://www.football-data.org/).
  

- ```API_FOOTBALL_DATA_ORG_API_KEY```
  

- ```API_FOOTBALL_COM_API_KEY```

### Setting up MySQL database

- In the file ```src/com/sql/SQL.java```, enter your MySQL database credentials (host, port number, database, user and
  password)
    - ```host```
    - ```portNo```
    - ```database```
    - ```user```
    - ```password```
  


- Execute all the SQL statements in the file ```src/setup/query.sql```

### Populate Data

- Run the file ```src/setup/Setup.java``` which will populate all the required data from the API into the database (it
  takes time).

### Launching the application

- To launch the application, run the ```src/com/football_manager/Main.java``` after setting up all the requirements
  listed above.
- Password for all managers is ```samplepwd```

## Screenshots

Login | Dashboard
------------ | -------------
![Login](screenshots/login.png) | ![Dashboard](screenshots/dashboard.png)

League Standings | Top Scorers
------------ | -------------
![League Standings](screenshots/standings.png) | ![Top Scorers](screenshots/topscorers.png)

Results | Fixtures
------------ | -------------
![Results](screenshots/results.png) | ![Fixtures](screenshots/fixtures.png)

Players | Filter by Position
------------ | -------------
![Players](screenshots/players.png) | ![Filter by Position](screenshots/filter_by_position.png)

Filter by Country | Filter by Team
------------ | -------------
![Filter by Country](screenshots/filter_by_country.png) | ![Filter by Team](screenshots/filter_by_team.png)

Filter by Age | Players After Filter
------------ | -------------
![Filter by Age](screenshots/filter_by_age.png) | ![Players After Filter](screenshots/players_after_filter.png)

Player Data | Transfer Type
------------ | -------------
![Player Data](screenshots/player_data.png) | ![Transfer Type](screenshots/transfer_type.png)

Permanent Transfer | Loan Transfer
------------ | -------------
![Permanent Transfer](screenshots/permanent_transfer.png) | ![Loan Transfer](screenshots/loan_transfer.png)

Player Exchange Transfer | Outgoing Transfer Requests
------------ | -------------
![Player Exchange Transfer](screenshots/player_exchange_transfer.png) | ![Outgoing Transfer Requests](screenshots/outgoing_transfer.png)

Incoming Transfer Requests | Accept/Reject Transfer
------------ | -------------
![Incoming Transfer Requests](screenshots/incoming_transfer.png) | ![Accept/Reject Transfer](screenshots/transfer_action.png)

 
