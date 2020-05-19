package com.trainigcenter.springtask.service;

import com.trainigcenter.springtask.domain.Genre;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface GenreService {

    Genre getGenreById(int genreId);

    Set<Genre> getAll();

    void addGenre(Genre genre);

    Genre updateGenre(Genre genre);

    boolean deleteGenre(Genre genre);

    Genre getGenreByIdWithMovies(Integer id);
}
