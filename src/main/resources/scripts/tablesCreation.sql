CREATE TABLE if not exists public.users
(
    user_id int NOT NULL,
    user_name varchar(50) NOT NULL,
    password varchar(50) NOT NULL,
    email varchar(50),
    points int NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (user_id)
);

CREATE TABLE if not exists public.teams
(
    team_id int NOT NULL,
    team_name varchar(50) NOT NULL,
    CONSTRAINT teams_pkey PRIMARY KEY (team_id)
);

CREATE TABLE if not exists public.matches
(
    match_id int NOT NULL,
    team_one_id int NOT NULL,
    team_two_id int NOT NULL,
    status varchar(50),
    start_time timestamp NOT NULL, 
    CONSTRAINT matches_pkey PRIMARY KEY (match_id),
    CONSTRAINT matches_fkey1 FOREIGN KEY (team_one_id) REFERENCES public.teams (team_id) ON UPDATE NO ACTION,
    CONSTRAINT matches_fkey2 FOREIGN KEY (team_two_id) REFERENCES public.teams (team_id) ON UPDATE NO ACTION
);

CREATE TABLE if not exists public.bid
(
    bid_id int NOT NULL,
    bid_points int NOT NULL,
    bid_type varchar(50) NOT NULL,
    CONSTRAINT bid_pkey PRIMARY KEY (bid_id)
);

--DROP TABLE public.user_bid CASCADE;
--CREATE TABLE if not exists public.user_bid
--(
--	
 --   user_id int NOT NULL,
  --  bid_id int NOT NULL,
   -- CONSTRAINT user_bid_pkey PRIMARY KEY (user_id,bid_id),
    --CONSTRAINT user_bid_fkey1 FOREIGN KEY (user_id) REFERENCES public.users (user_id) ON UPDATE NO ACTION,
    --CONSTRAINT user_bid_fkey2 FOREIGN KEY (bid_id) REFERENCES public.bid (bid_id) ON UPDATE NO ACTION
--);

CREATE TABLE if not exists public.user_matches
(
    user_id int NOT NULL,
    match_id int NOT NULL,
    team_id int NOT NULL,
    bid_id int NOT NULL,
    points int,
    CONSTRAINT user_matches_pkey PRIMARY KEY (user_id,match_id,team_id,bid_id),
    CONSTRAINT user_matches_fkey1 FOREIGN KEY (user_id) REFERENCES public.users (user_id) ON UPDATE NO ACTION,
    CONSTRAINT user_matches_fkey2 FOREIGN KEY (bid_id) REFERENCES public.bid (bid_id) ON UPDATE NO ACTION,
	CONSTRAINT user_matches_fkey3 FOREIGN KEY (match_id) REFERENCES public.matches (match_id) ON UPDATE NO ACTION,
    CONSTRAINT user_matches_fkey4 FOREIGN KEY (team_id) REFERENCES public.teams (team_id) ON UPDATE NO ACTION
);

--CREATE TABLE if not exists public.result
--(
 --   result_id int NOT NULL,
  --  result_details varchar(50) NOT NULL,
   -- CONSTRAINT result_pkey PRIMARY KEY (result_id)
--);

--CREATE TABLE if not exists public.match_results
--(
--    match_id int NOT NULL,
--    result_id int NOT NULL,
--    team_id int,
--    CONSTRAINT match_results_pkey PRIMARY KEY (match_id,result_id),
--   	CONSTRAINT match_results_fkey1 FOREIGN KEY (match_id) REFERENCES public.matches (match_id) ON UPDATE NO ACTION,
--   	CONSTRAINT match_results_fkey2 FOREIGN KEY (result_id) REFERENCES public.result (result_id) ON UPDATE NO ACTION,
--   	CONSTRAINT match_results_fkey3 FOREIGN KEY (team_id) REFERENCES public.teams (team_id) ON UPDATE NO ACTION
--);
