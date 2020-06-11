package com.trainigcenter.springtask.service;

import com.trainigcenter.springtask.domain.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MovieService {

    Page<Movie> getAll(Pageable pageable);

    Optional<Movie> getById(Integer id);

    Movie create(Movie movie);

    Movie update(Movie movie, int id);

    void delete(Integer id);
}
