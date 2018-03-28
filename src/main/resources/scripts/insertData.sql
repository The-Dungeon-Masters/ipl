INSERT INTO ipl.user_types(user_type)	VALUES ('Admin');
INSERT INTO ipl.user_types(user_type)	VALUES ('User');

INSERT INTO ipl.users(user_id, user_name, password, email, points, user_type, created_by)
VALUES (1, 'superadmin', 'superadmin','fakePassword', 0, 'Admin', 'Default');

INSERT INTO ipl.teams(team_id, team_name, team_full_name)	VALUES (1, 'MI', 'MUMBAI INDIANS');
INSERT INTO ipl.teams(team_id, team_name, team_full_name)	VALUES (2, 'KKR', 'KOLKATA KNIGHT RIDERS');
INSERT INTO ipl.teams(team_id, team_name, team_full_name)	VALUES (3, 'DD', 'DELHI DAREDEVILS');
INSERT INTO ipl.teams(team_id, team_name, team_full_name)	VALUES (4, 'CSK', 'CHENNAI SUPER KINGS');
INSERT INTO ipl.teams(team_id, team_name, team_full_name)	VALUES (5, 'KXIP', 'KINGS XI PUNJAB');
INSERT INTO ipl.teams(team_id, team_name, team_full_name)	VALUES (6, 'RR', 'RAJASTHAN ROYALS');
INSERT INTO ipl.teams(team_id, team_name, team_full_name)	VALUES (7, 'RCB', 'ROYAL CHALLENGERS BANGALORE');
INSERT INTO ipl.teams(team_id, team_name, team_full_name)	VALUES (8, 'SRH', 'SUNRISERS HYDERABAD');

INSERT INTO ipl.contest(	contest_id, contest_points, contest_type)	VALUES (1, 0, 'lunch');
INSERT INTO ipl.contest(	contest_id, contest_points, contest_type)	VALUES (2, 10, '10 Points');
INSERT INTO ipl.contest(	contest_id, contest_points, contest_type)	VALUES (3, 50, '50 Points');


