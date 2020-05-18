package com.trainigcenter.springtask.service;

import com.trainigcenter.springtask.domain.Genre;

import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public interface GenreService {

    public Genre getGenreById(int genreId);
    public Set<Genre> getAll();
    public Genre getGenreByName(String genreName);
    public void addGenre(Genre genre);
    public Genre updateGenre(Genre genre);
    public void deleteGenre(Genre genre);
}
