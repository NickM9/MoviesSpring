package com.trainigcenter.springtask.service;

import com.trainigcenter.springtask.domain.Movie;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface MovieService {

    Page<Movie> getAll(int page, int size);

    Optional<Movie> getById(Integer id);

    Movie create(Movie movie);

    Movie update(Movie movie, Integer id);

    void delete(Integer id);
}
