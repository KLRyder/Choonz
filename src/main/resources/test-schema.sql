CREATE TABLE artist (
    artistid     INT,
    name         VARCHAR(45)  NOT NULL,
    password     VARCHAR(45)  NOT NULL,
    description  VARCHAR(250) NOT NULL,
    PRIMARY KEY (artistid)
                    );
 
CREATE TABLE album  (
    albumid     INT,
    cover       VARCHAR(200) NOT NULL,
    name        VARCHAR(45)  NOT NULL,
    fk_artistid INT          NOT NULL,
    PRIMARY KEY (albumid),
    FOREIGN KEY (fk_artistid) REFERENCES artist (artistid)
                   );
CREATE TABLE genre  (
    genreid     INT,
    name        VARCHAR(100) NOT NULL,
    description VARCHAR(250) NOT NULL,
    PRIMARY KEY (genreid)
                    );
CREATE TABLE track  (
    trackid     INT,
    name        VARCHAR(100)  NOT NULL,
    lyrics      VARCHAR(1000) NOT NULL,
    duration    INT           NOT NULL,
    track_number INT          NOT NULL,
    fk_albumid   INT          NOT NULL,
    fk_genreid   INT          NOT NULL,
    PRIMARY KEY (trackid),
    UNIQUE (fk_albumid, track_number),
    FOREIGN KEY (fk_albumid) REFERENCES album (albumid),
    FOREIGN KEY (fk_genreid) REFERENCES genre (genreid)
                   );
   
CREATE TABLE playlist (
    playlistid  INT,
    name        VARCHAR(100)  NOT NULL,
    artwork     VARCHAR(45)   NOT NULL,
    description VARCHAR(200)  NOT NULL,
    PRIMARY KEY (playlistid)
                      );

CREATE TABLE playlisttrack (
    fk_playlistid INT         NOT NULL,
    fk_trackid    INT         NOT NULL,
    track_number  INT         NOT NULL,
    PRIMARY KEY (fk_playlistid, fk_trackid),
    UNIQUE (fk_playlistid, track_number),
    FOREIGN KEY (fk_playlistid)  REFERENCES playlist (playlistid),
    FOREIGN KEY (fk_trackid)     REFERENCES track (trackid)
                           );
