CREATE DATABASE movies_db;

-- Actors table
CREATE TABLE public.actors
(
    id serial NOT NULL,
    name character varying(45) NOT NULL,
    birth_year integer NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);

ALTER TABLE public.actors
    OWNER to postgres;

-- Genres table
CREATE TABLE public.genres
(
    id serial NOT NULL,
    name character varying(45) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);

ALTER TABLE public.genres
    OWNER to postgres;

-- Movies table
CREATE TABLE public.movie
(
    id serial NOT NULL,
    title character varying(140) NOT NULL,
    description character varying(500) NOT NULL,
    duration integer NOT NULL,
    PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.movie
    OWNER to postgres;


-- Reviews table
CREATE TABLE public.reviews
(
    id serial NOT NULL,
    movie_id integer NOT NULL,
    author_name character varying(45) NOT NULL,
    title character varying(45) NOT NULL,
    text text NOT NULL,
    rating double precision NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (movie_id)
        REFERENCES public.movie (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
);

ALTER TABLE public.reviews
    OWNER to postgres;

-- movie_actors table
CREATE TABLE public.movie_actors
(
    movie_id integer NOT NULL,
    actor_id integer NOT NULL,
    PRIMARY KEY (movie_id, actor_id),
    FOREIGN KEY (movie_id)
        REFERENCES public.movie (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
    FOREIGN KEY (actor_id)
        REFERENCES public.actors (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID
);

ALTER TABLE public.movie_actors
    OWNER to postgres;

-- movie_genres table
CREATE TABLE public.movie_genres
(
    movie_id integer NOT NULL,
    genre_id integer NOT NULL,
    PRIMARY KEY (movie_id, genre_id),
    FOREIGN KEY (movie_id)
        REFERENCES public.movie (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
    FOREIGN KEY (genre_id)
        REFERENCES public.genres (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID
);

ALTER TABLE public.movie_genres
    OWNER to postgres;

-- some start scripts
INSERT INTO public.actors(name, birth_year) VALUES ('Robert Downey Jr.', 1965);
INSERT INTO public.actors(name, birth_year) VALUES ('Chris Evans', 1981);
INSERT INTO public.actors(name, birth_year) VALUES ('Chris Hemsworth', 1983);

INSERT INTO public.genres(name) VALUES ('Action');
INSERT INTO public.genres(name) VALUES ('Comedy');
INSERT INTO public.genres(name) VALUES ('Science fiction');

INSERT INTO public.movie(title, description, duration) VALUES ('The Avengers', 'Avengers description. Very good!', 8520);

INSERT INTO public.reviews(movie_id, author_name, title, text, rating)
	VALUES (1, 'Joe Russo', 'Tittle, very good film!', 'The big text abount The avengers film', 8.7);

INSERT INTO public.movie_actors(movie_id, actor_id) VALUES (1, 2)
INSERT INTO public.movie_actors(movie_id, actor_id) VALUES (1, 1)
INSERT INTO public.movie_actors(movie_id, actor_id) VALUES (1, 3)

INSERT INTO public.movie_genres(movie_id, genre_id) VALUES (1, 1);
INSERT INTO public.movie_genres(movie_id, genre_id) VALUES (1, 3);
INSERT INTO public.movie_genres(movie_id, genre_id) VALUES (1, 4);


