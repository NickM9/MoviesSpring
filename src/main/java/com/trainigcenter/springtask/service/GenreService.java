package com.trainigcenter.springtask.service;

import com.trainigcenter.springtask.domain.Genre;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GenreService {

    Genre getById(Integer id);

    List<Genre> getAll();

    Genre add(Genre genre);

    Genre update(Genre genre);

    boolean delete(Genre genre);

    Genre getByIdWithMovies(Integer id);
}
