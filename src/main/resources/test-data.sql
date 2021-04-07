USE qa_music_project;

INSERT INTO artist (artistid, name, password, description)
            VALUES (1, 'Artist name 1', 'wefdsoiu', 'Artist description 1');
INSERT INTO artist (artistid, name, password, description)
            VALUES (2, 'Artist name 2', 'zxbnvjjn', 'Artist description 2');

INSERT INTO album  (albumid, cover, name, fk_artistid)
            VALUES (101, 'Cover 101', 'Album by artist 1', 1);
INSERT INTO album  (albumid, cover, name, fk_artistid)
            VALUES (102, 'Cover 102', 'Album by artist 2', 2);

INSERT INTO genre  (genreid, name, description)
            VALUES (201, 'Genre name 201', 'Genre description 201');
INSERT INTO genre  (genreid, name, description)
            VALUES (202, 'Genre name 202', 'Genre description 202');