CREATE SCHEMA dockerspringmovies;

-- Actors table
CREATE TABLE dockerspringmovies.actors
(
    id serial NOT NULL,
    name character varying(45) NOT NULL,
    birth_year integer NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);

ALTER TABLE dockerspringmovies.actors
    OWNER to postgres;

-- Genres table
CREATE TABLE dockerspringmovies.genres
(
    id serial NOT NULL,
    name character varying(45) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);

ALTER TABLE dockerspringmovies.genres
    OWNER to postgres;

-- Movies table
CREATE TABLE dockerspringmovies.movie
(
    id serial NOT NULL,
    title character varying(140) NOT NULL,
    description character varying(500) NOT NULL,
    duration integer NOT NULL,
    PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE dockerspringmovies.movie
    OWNER to postgres;


-- Reviews table
CREATE TABLE dockerspringmovies.reviews
(
    id serial NOT NULL,
    movie_id integer NOT NULL,
    author_name character varying(45) NOT NULL,
    title character varying(45) NOT NULL,
    text text NOT NULL,
    rating double precision NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (movie_id)
        REFERENCES dockerspringmovies.movie (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
);

ALTER TABLE dockerspringmovies.reviews
    OWNER to postgres;

-- movie_actors table
CREATE TABLE dockerspringmovies.movie_actors
(
    movie_id integer NOT NULL,
    actor_id integer NOT NULL,
    PRIMARY KEY (movie_id, actor_id),
    FOREIGN KEY (movie_id)
        REFERENCES dockerspringmovies.movie (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
    FOREIGN KEY (actor_id)
        REFERENCES dockerspringmovies.actors (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID
);

ALTER TABLE dockerspringmovies.movie_actors
    OWNER to postgres;

-- movie_genres table
CREATE TABLE dockerspringmovies.movie_genres
(
    movie_id integer NOT NULL,
    genre_id integer NOT NULL,
    PRIMARY KEY (movie_id, genre_id),
    FOREIGN KEY (movie_id)
        REFERENCES dockerspringmovies.movie (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
    FOREIGN KEY (genre_id)
        REFERENCES dockerspringmovies.genres (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID
);

ALTER TABLE dockerspringmovies.movie_genres
    OWNER to postgres;

-- some start scripts
INSERT INTO dockerspringmovies.actors(name, birth_year) VALUES ('Robert Downey Jr.', 1965);
INSERT INTO dockerspringmovies.actors(name, birth_year) VALUES ('Chris Evans', 1981);
INSERT INTO dockerspringmovies.actors(name, birth_year) VALUES ('Chris Hemsworth', 1983);

INSERT INTO dockerspringmovies.genres(name) VALUES ('action');
INSERT INTO dockerspringmovies.genres(name) VALUES ('comedy');
INSERT INTO dockerspringmovies.genres(name) VALUES ('science fiction');

INSERT INTO dockerspringmovies.movie(title, description, duration) VALUES ('The Avengers', 'Avengers description. Very good!', 8520);

INSERT INTO dockerspringmovies.reviews(movie_id, author_name, title, text, rating)
	VALUES (1, 'Joe Russo', 'Tittle, very good film!', 'The big text abount The avengers film', 8.7);

INSERT INTO dockerspringmovies.movie_actors(movie_id, actor_id) VALUES (1, 1);
INSERT INTO dockerspringmovies.movie_actors(movie_id, actor_id) VALUES (1, 2);
INSERT INTO dockerspringmovies.movie_actors(movie_id, actor_id) VALUES (1, 3);

INSERT INTO dockerspringmovies.movie_genres(movie_id, genre_id) VALUES (1, 1);
INSERT INTO dockerspringmovies.movie_genres(movie_id, genre_id) VALUES (1, 2);
INSERT INTO dockerspringmovies.movie_genres(movie_id, genre_id) VALUES (1, 3);