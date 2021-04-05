
INSERT INTO Album (name, fk_artistid, fk_genreid)
VALUES ('RickRoll', 1, 1); 

INSERT INTO Artist (name)
VALUES ('TeamTwo');

INSERT INTO Track (name, duration, lyrics, fk_albumid, fk_playlistid)
VALUES ('PickleRick', 180, 'Im Pickle Rick', 1, 1);

INSERT INTO Genre (name, description)
VALUES ('RickingAndRolling', 'MortySucks');

INSERT INTO Playlist (name, description, artwork)
VALUES ('personal', 'mySongs', 'plain');

