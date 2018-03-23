INSERT INTO public.users(user_id, user_name, password, email, points)	VALUES (1, 'nakul', 'nakul',null, 100);
INSERT INTO public.users(user_id, user_name, password, email, points)	VALUES (2, 'yogesh', 'yogesh',null, 200);


INSERT INTO public.teams(team_id, team_name)	VALUES (1, 'MI');
INSERT INTO public.teams(team_id, team_name)	VALUES (2, 'KKR');


INSERT INTO public.matches(	match_id, team_one_id, team_two_id, start_time)	VALUES (1, 1, 2, to_timestamp('20-04-2018 15:36:38', 'dd-mm-yyyy hh24:mi:ss'));
INSERT INTO public.matches(	match_id, team_one_id, team_two_id, start_time)	VALUES (2, 2, 1, to_timestamp('16-04-2018 15:36:38', 'dd-mm-yyyy hh24:mi:ss'));


INSERT INTO public.bid(	bid_id, bid_points, bid_type)	VALUES (1, 0, 'niyuj');
INSERT INTO public.bid(	bid_id, bid_points, bid_type)	VALUES (2, 10, '10 pts');
INSERT INTO public.bid(	bid_id, bid_points, bid_type)	VALUES (3, 50, '50 pts');

INSERT INTO public.user_matches(	user_id, match_id, team_id, bid_id)	VALUES (1, 1, 2, 2);
INSERT INTO public.user_matches(	user_id, match_id, team_id, bid_id)	VALUES (2, 1, 2, 3);
