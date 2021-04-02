-- DB Creation

create database footballmanager;

use footballmanager;

-- Table Creation

create table managers (
    manager_id int NOT NULL,
    name varchar(255),
    country varchar(255),
    age int,
    username varchar(255),
    password varchar(255),
    PRIMARY KEY (manager_id)
);

create table teams (
    team_id BIGINT NOT NULL,
    manager_id int,
    name varchar(255),
    badge varchar(512),
    PRIMARY KEY (team_id),
    FOREIGN KEY (manager_id) REFERENCES managers(manager_id)
);

create table players (
    player_id BIGINT NOT NULL,
    team_id BIGINT,
    name varchar(255),
    shirt_number int,
    country varchar(255),
    position varchar(255),
    age int,
    matches_played int,
    goals_scored int,
    yellow_cards int,
    red_cards int,
    username varchar(255),
    password varchar(255),
    PRIMARY KEY (player_id),
    FOREIGN KEY (team_id) REFERENCES teams(team_id)
);