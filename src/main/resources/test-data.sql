USE qa_music_project;

INSERT INTO user_details(id, password, role, username)
VALUES (1, 'pass', 0, 'addy the admin');

INSERT INTO playlist(id, artwork, description, name, creator_id)
VALUES (1, 'artwork1', 'description1', 'name1', 1);

INSERT INTO artist (id, name)
VALUES (1, 'Artist name 1');

INSERT INTO album (id, cover, name, artist_id)
VALUES (1, 'Cover 1', 'Album by artist 1', 1);

INSERT INTO genre (id, name, description)
VALUES (1, 'Genre name 1', 'Genre description 1');

INSERT INTO track (id, duration, lyrics, name, album_id, genre_id)
VALUES (1, 100, 'lyrics1', 'name1', 1, 1);

INSERT INTO playlist_link (id, playlist_id, track_id)
VALUES (1,1,1);