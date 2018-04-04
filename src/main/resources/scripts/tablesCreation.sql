CREATE DATABASE if not exists ipl;

CREATE TABLE IF NOT EXISTS ipl.user_types (
  user_type VARCHAR(25) UNIQUE
);

CREATE TABLE if not exists ipl.users
(
    user_id int NOT NULL AUTO_INCREMENT,
    user_name varchar(50) NOT NULL,
    password varchar(80) NOT NULL,
    email varchar(50),
    points int NOT NULL,
    user_type VARCHAR(25) NOT NULL DEFAULT  "User",
    created_by varchar(50) NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (user_id),
    CONSTRAINT users_fkey1 FOREIGN KEY (user_type) REFERENCES ipl.user_types(user_type) ON UPDATE NO ACTION
);

CREATE TABLE if not exists ipl.teams
(
    team_id int NOT NULL AUTO_INCREMENT,
    team_name varchar(50) NOT NULL,
    team_full_name varchar(50) NOT NULL,
    CONSTRAINT teams_pkey PRIMARY KEY (team_id)
);

CREATE TABLE if not exists ipl.matches
(
    match_id int NOT NULL AUTO_INCREMENT,
    team_one_id int NOT NULL,
    team_two_id int NOT NULL,
    status varchar(50),
    start_time timestamp NOT NULL, 
    CONSTRAINT matches_pkey PRIMARY KEY (match_id),
    CONSTRAINT matches_fkey1 FOREIGN KEY (team_one_id) REFERENCES ipl.teams (team_id) ON UPDATE NO ACTION,
    CONSTRAINT matches_fkey2 FOREIGN KEY (team_two_id) REFERENCES ipl.teams (team_id) ON UPDATE NO ACTION
);

CREATE TABLE if not exists ipl.contest
(
    id int NOT NULL AUTO_INCREMENT,
    points float NOT NULL,
    type varchar(50) NOT NULL,
    CONSTRAINT contest_pkey PRIMARY KEY (id)
);

CREATE TABLE if not exists ipl.contest_users
(
    id int NOT NULL AUTO_INCREMENT,
    contest_id int NOT NULL,
    user_id int NOT NULL,
    CONSTRAINT contest_users_pkey PRIMARY KEY (id),
    CONSTRAINT contest_users_fkey1 FOREIGN KEY (contest_id) REFERENCES ipl.contest (id) ON UPDATE NO ACTION,
    CONSTRAINT contest_users_fkey2 FOREIGN KEY (user_id) REFERENCES ipl.users (user_id) ON UPDATE NO ACTION
);

CREATE TABLE if not exists ipl.user_recharge
(
    id int NOT NULL AUTO_INCREMENT,
    user_id int NOT NULL,
    recharge_points float NOT NULL,
    recharged_by int NOT NULL,
    comments varchar(50) NOT NULL,
    recharge_time timestamp NOT NULL,
    CONSTRAINT user_recharge_pkey PRIMARY KEY (id),
    CONSTRAINT user_recharge_fkey1 FOREIGN KEY (user_id) REFERENCES ipl.users (user_id) ON UPDATE NO ACTION,
    CONSTRAINT user_recharge_fkey2 FOREIGN KEY (recharged_by) REFERENCES ipl.users (user_id) ON UPDATE NO ACTION
);

CREATE TABLE if not exists ipl.user_matches
(
	id int NOT NULL AUTO_INCREMENT,
    user_contest_id int NOT NULL,
    match_id int NOT NULL,
    team_id int NOT NULL,
    points float,
    CONSTRAINT user_matches_pkey PRIMARY KEY (id),
    CONSTRAINT user_matches_fkey1 FOREIGN KEY (user_contest_id) REFERENCES ipl.contest_users (id) ON UPDATE NO ACTION,
    CONSTRAINT user_matches_fkey3 FOREIGN KEY (match_id) REFERENCES ipl.matches (match_id) ON UPDATE NO ACTION,
    CONSTRAINT user_matches_fkey4 FOREIGN KEY (team_id) REFERENCES ipl.teams (team_id) ON UPDATE NO ACTION
);

CREATE TABLE if not exists ipl.match_results
(
    match_id int NOT NULL,
    team_id int,
    CONSTRAINT match_results_pkey PRIMARY KEY (match_id),
    CONSTRAINT match_results_fkey1 FOREIGN KEY (match_id) REFERENCES ipl.matches (match_id) ON UPDATE NO ACTION,
    CONSTRAINT match_results_fkey3 FOREIGN KEY (team_id) REFERENCES ipl.teams (team_id) ON UPDATE NO ACTION
);
